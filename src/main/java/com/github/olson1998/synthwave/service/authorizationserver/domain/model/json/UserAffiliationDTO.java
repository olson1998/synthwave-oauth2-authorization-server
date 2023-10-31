package com.github.olson1998.synthwave.service.authorizationserver.domain.model.json;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAffiliation;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserAffiliationDTO implements UserAffiliation {

    public static final String USER_AFFILIATION_USER_ID_JSON_FIELD = "uid";

    public static final String USER_AFFILIATION_CODE_JSON_FIELD = "code";

    public static final String USER_AFFILIATION_DIVI_JSON_FIELD = "divi";

    private final TSID userId;

    private final String companyCode;

    private final String division;

}
