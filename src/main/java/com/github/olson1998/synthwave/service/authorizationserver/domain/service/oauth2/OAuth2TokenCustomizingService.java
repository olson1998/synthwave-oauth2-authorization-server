package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.SynthwaveUser;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

public class OAuth2TokenCustomizingService implements OAuth2TokenCustomizer<JwtEncodingContext> {

    @Override
    public void customize(JwtEncodingContext context) {
        if(context.getPrincipal() instanceof SynthwaveUser synthwaveUser) {
            context.getClaims().claims(jwtClaims -> {
                jwtClaims.put("USID", synthwaveUser.getUserId());
                jwtClaims.put("CODE", synthwaveUser.getCompanyCode());
                jwtClaims.put("DIVI", synthwaveUser.getCompanyCode());
            });
        }
    }
}
