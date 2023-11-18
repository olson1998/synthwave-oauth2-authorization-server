package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.JwtCustomizer;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.AbstractSynthWaveRegisteredClient;
import lombok.NonNull;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;

public class SynthWaveJWTCustomizer implements JwtCustomizer {

    public static final String COMPANY_CODE_CLAIM = "code";

    public static final String DIVISION_CLAIM = "divi";

    @Override
    public void customize(@NonNull JwtEncodingContext context) {
        if(context.getRegisteredClient() instanceof AbstractSynthWaveRegisteredClient abstractSynthWaveRegisteredClient){
            context.getClaims()
                    .claim(COMPANY_CODE_CLAIM, abstractSynthWaveRegisteredClient.getCompanyCode())
                    .claim(DIVISION_CLAIM, abstractSynthWaveRegisteredClient.getDivision());
        }
    }
}
