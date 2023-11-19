package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import io.hypersistence.tsid.TSID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.Period;

@Data
@AllArgsConstructor
public class ExtendedUserEntityImpl implements UserEntity, Affiliation {
    
    private final TSID id;
    
    private final String username;
    
    private final boolean enabled;
    
    private final Period expirePeriod;
    
    private final String companyCode;
    
    private final String division;
}