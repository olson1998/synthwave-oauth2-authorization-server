package com.github.olson1998.synthwave.service.authorizationserver.adapter.csrf;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.csrf.repository.ConfigurableCsrfTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/csrf")
public class CsrfRestController {

    private final ConfigurableCsrfTokenRepository csrfTokenRepository;

    @GetMapping(produces = TEXT_PLAIN_VALUE)
    public String generateCsrf(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        CsrfToken csrf = csrfTokenRepository.generateToken(httpServletRequest);
        csrfTokenRepository.saveToken(csrf, httpServletRequest, httpServletResponse);
        return csrf.getToken();
    }

}
