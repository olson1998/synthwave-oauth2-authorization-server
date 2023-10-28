package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.SynthWaveJwtCustomizer;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveRegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;

public class SynthWaveOAuth2JwtCustomizer implements SynthWaveJwtCustomizer {

    public static final String COMPANY_CODE_CLAIM = "code";

    public static final String DIVISION_CLAIM = "divi";

    @Override
    public void customize(JwtEncodingContext context) {
        if(context.getRegisteredClient() instanceof SynthWaveRegisteredClient synthWaveRegisteredClient){
            context.getClaims()
                    .claim(COMPANY_CODE_CLAIM, synthWaveRegisteredClient.getCompanyCode())
                    .claim(DIVISION_CLAIM, synthWaveRegisteredClient.getDivision());
        }
    }
}
