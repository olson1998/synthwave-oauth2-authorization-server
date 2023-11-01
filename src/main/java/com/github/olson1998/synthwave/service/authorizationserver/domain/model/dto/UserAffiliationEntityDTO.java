package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAffiliationEntity;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserAffiliationEntityDTO implements UserAffiliationEntity {

    public static final String USER_AFFILIATION_USER_ID_JSON_FIELD = "uid";

    public static final String USER_AFFILIATION_CODE_JSON_FIELD = "code";

    public static final String USER_AFFILIATION_DIVI_JSON_FIELD = "divi";

    private final TSID userId;

    private final String companyCode;

    private final String division;

}
