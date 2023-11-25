package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.AffiliationModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AffiliationEntity;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AffiliationEntityModel extends AffiliationModel implements AffiliationEntity {

    public static final String USER_AFFILIATION_USER_ID_JSON_FIELD = "user_id";

    private final TSID userId;

    public AffiliationEntityModel(TSID userId, String companyCode, String division) {
        super(companyCode, division);
        this.userId = userId;
    }
}
