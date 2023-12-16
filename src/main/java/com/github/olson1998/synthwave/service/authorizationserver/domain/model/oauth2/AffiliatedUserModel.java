package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import io.hypersistence.tsid.TSID;
import lombok.*;
import org.joda.time.MutableDateTime;
import org.joda.time.Period;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class AffiliatedUserModel implements UserEntity, Affiliation {
    
    private final TSID id;
    
    private final String username;
    
    private final Boolean enabled;
    
    private final MutableDateTime expireDateTime;
    
    private final String companyCode;
    
    private final String division;
}
