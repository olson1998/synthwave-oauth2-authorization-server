package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import lombok.*;

@Data
@AllArgsConstructor
public class AffiliationModel implements Affiliation {

    public static final String USER_AFFILIATION_CODE_JSON_FIELD = "code";

    public static final String USER_AFFILIATION_DIVI_JSON_FIELD = "divi";

    private final String companyCode;

    private final String division;
}
