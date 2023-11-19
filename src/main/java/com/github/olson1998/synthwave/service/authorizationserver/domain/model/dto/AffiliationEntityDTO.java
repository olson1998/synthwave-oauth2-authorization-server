package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AffiliationEntity;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class AffiliationEntityDTO extends AffiliationDTO implements AffiliationEntity {

    public static final String USER_AFFILIATION_USER_ID_JSON_FIELD = "uid";

    private final TSID userId;

    public AffiliationEntityDTO(TSID userId, String companyCode, String division) {
        super(companyCode, division);
        this.userId = userId;
    }
}
