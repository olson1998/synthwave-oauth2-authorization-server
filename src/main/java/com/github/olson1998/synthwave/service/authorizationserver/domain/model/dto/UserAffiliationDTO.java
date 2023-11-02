package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserAffiliation;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserAffiliationDTO implements UserAffiliation {

    public static final String USER_AFFILIATION_CODE_JSON_FIELD = "code";

    public static final String USER_AFFILIATION_DIVI_JSON_FIELD = "divi";

    private final String companyCode;

    private final String division;
}
