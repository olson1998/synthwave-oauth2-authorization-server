package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.JwtCustomizer;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.DefaultRegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;

public class SynthWaveOAuth2JwtCustomizer implements JwtCustomizer {

    public static final String COMPANY_CODE_CLAIM = "code";

    public static final String DIVISION_CLAIM = "divi";

    @Override
    public void customize(JwtEncodingContext context) {
        if(context.getRegisteredClient() instanceof DefaultRegisteredClient defaultRegisteredClient){
            context.getClaims()
                    .claim(COMPANY_CODE_CLAIM, defaultRegisteredClient.getCompanyCode())
                    .claim(DIVISION_CLAIM, defaultRegisteredClient.getDivision());
        }
    }
}
