package com.github.olson1998.synthwave.service.authorizationserver.domain.service.csrf;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.csrf.CsrfSessionToken;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.csrf.CsrfSessionTokens;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.SynthwaveUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.csrf.repository.ConfigurableCsrfTokenRepository;
import com.github.olson1998.synthwave.support.csrf.exception.CsrfUnexpectedFieldException;
import com.github.olson1998.synthwave.support.csrf.model.CsrfEntity;
import com.github.olson1998.synthwave.support.csrf.model.CsrfPrincipal;
import com.github.olson1998.synthwave.support.csrf.model.DecodedCsrf;
import com.github.olson1998.synthwave.support.csrf.serial.CsrfDeserializer;
import com.github.olson1998.synthwave.support.csrf.serial.CsrfSerializer;
import com.github.olson1998.synthwave.support.csrf.serial.CsrfVerifier;
import com.github.olson1998.synthwave.support.joda.converter.JavaDurationConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.binary.AES256BinaryEncryptor;
import org.joda.time.MutableDateTime;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.UUID;

import static com.github.olson1998.synthwave.support.csrf.model.CsrfParameters.CSRF_PARAMETER;
import static com.github.olson1998.synthwave.support.csrf.model.CsrfParameters.X_SYNTHWAVE_CSRF;

@Slf4j
@RequiredArgsConstructor
public class CsrfTokenService implements ConfigurableCsrfTokenRepository {
    
    public static final String HTTP_SESSION_CSRF_TOKENS_ATTRIBUTE = "HttpSessionCSRF";

    private final String servletContextPath;

    private final Duration csrfExpireDuration;

    private final AES256BinaryEncryptor csrfPublicSignatory;

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CsrfEntity csrfEntity;
        MutableDateTime timestamp = MutableDateTime.now();
        URI issuer = resolveIssuerURI(request.getRequestURL());
        HttpSession httpSession = request.getSession();
        if(authentication instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken &&
                usernamePasswordAuthenticationToken.getPrincipal() instanceof SynthwaveUser synthwaveUser) {
            csrfEntity = synthwaveUserCsrfEntity(synthwaveUser, timestamp, issuer);
        } else {
            csrfEntity = anonymousCsrfEntity(timestamp, issuer);
        }
        AES256BinaryEncryptor csrfPrivateSignatory = new AES256BinaryEncryptor();
        csrfPrivateSignatory.setPassword(httpSession.getId());
        try {
            String csrf = CsrfSerializer.serialize(csrfEntity, csrfPublicSignatory, csrfPrivateSignatory);
            return new DefaultCsrfToken(X_SYNTHWAVE_CSRF, CSRF_PARAMETER, csrf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        try{
            DecodedCsrf decodedCsrf = CsrfDeserializer.deserialize(token.getToken());
            HttpSession httpSession = request.getSession();
            Object httpSessionCsrfTokensAttribute = httpSession .getAttribute(HTTP_SESSION_CSRF_TOKENS_ATTRIBUTE);
            log.info("Issued CSRF: {} HttpSession={}", decodedCsrf, httpSession .getId());
            if(httpSessionCsrfTokensAttribute instanceof CsrfSessionTokens csrfSessionTokens) {
                CsrfSessionToken csrfSessionToken = new CsrfSessionToken(decodedCsrf);
                csrfSessionTokens.add(csrfSessionToken);
            } else {
                var csrfSessionToken = new CsrfSessionTokens();
                csrfSessionToken.add(new CsrfSessionToken(decodedCsrf));
                httpSession.setAttribute(HTTP_SESSION_CSRF_TOKENS_ATTRIBUTE, csrfSessionToken);
            }
        } catch (IOException | URISyntaxException | CsrfUnexpectedFieldException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SneakyThrows
    public CsrfToken loadToken(HttpServletRequest request) {
        CsrfToken csrfToken = null;
        if(request.getHeader(X_SYNTHWAVE_CSRF) != null) {
            var csrf = request.getHeader(X_SYNTHWAVE_CSRF);
            csrfToken = new DefaultCsrfToken(X_SYNTHWAVE_CSRF, CSRF_PARAMETER, csrf);
        } else if(request.getParameter(CSRF_PARAMETER) != null) {
            var csrf = request.getParameter(CSRF_PARAMETER);
            csrfToken = new DefaultCsrfToken(X_SYNTHWAVE_CSRF, CSRF_PARAMETER, csrf);
        }
        if(csrfToken != null) {
            var csrf = csrfToken.getToken();
            var decodedCsrf = CsrfDeserializer.deserialize(csrf);
            HttpSession httpSession = request.getSession();
            Object httpSessionCsrfTokensAttribute = httpSession .getAttribute(HTTP_SESSION_CSRF_TOKENS_ATTRIBUTE);
            if(httpSessionCsrfTokensAttribute instanceof CsrfSessionTokens csrfSessionTokens) {
                CsrfSessionToken csrfSessionToken = csrfSessionTokens.findByTokenId(decodedCsrf.getTokenId())
                        .orElseThrow();
                AES256BinaryEncryptor csrfPrivateSignatory = new AES256BinaryEncryptor();
                csrfPrivateSignatory.setPassword(httpSession.getId());
                CsrfVerifier.verify(decodedCsrf, csrfPublicSignatory, csrfPrivateSignatory);
            }
        }
        return csrfToken;
    }
    
    private CsrfEntity synthwaveUserCsrfEntity(SynthwaveUser synthwaveUser, MutableDateTime timestamp, URI issuerURI) {
        MutableDateTime expireTimestamp = (MutableDateTime) timestamp.clone();
        expireTimestamp.add(new JavaDurationConverter(csrfExpireDuration).toDuration());
        return new CsrfEntity(
                UUID.randomUUID(),
                new CsrfPrincipal(synthwaveUser.getUserId(), new ArrayList<>(), new ArrayList<>()),
                issuerURI,
                timestamp,
                expireTimestamp
        );
    }

    private CsrfEntity anonymousCsrfEntity(MutableDateTime timestamp, URI issuerURI) {
        MutableDateTime expireTimestamp = (MutableDateTime) timestamp.clone();
        expireTimestamp.add(new JavaDurationConverter(csrfExpireDuration).toDuration());
        return new CsrfEntity(
                UUID.randomUUID(),
                new CsrfPrincipal(-1, new ArrayList<>(), new ArrayList<>()),
                issuerURI,
                timestamp,
                expireTimestamp
        );
    }

    @SneakyThrows
    private URI resolveIssuerURI(StringBuffer url) {
        URI requestURI = new URI(url.toString());
        StringBuilder issuerURI = new StringBuilder(requestURI.getScheme())
                .append("://")
                .append(requestURI.getHost());
        if(requestURI.getPort() > 0) {
            issuerURI.append(':').append(requestURI.getPort());
        }
        if(servletContextPath != null) {
            issuerURI.append(servletContextPath);
        }
        return new URI(issuerURI.toString());
    }
    
}
