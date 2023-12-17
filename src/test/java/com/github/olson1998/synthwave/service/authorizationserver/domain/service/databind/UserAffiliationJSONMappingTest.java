package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.AffiliationEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AffiliationEntity;
import io.hypersistence.tsid.TSID;

class UserAffiliationJSONMappingTest extends AuthorizationServerObjectMappingTest<AffiliationEntity> {

    UserAffiliationJSONMappingTest() {
        super(AffiliationEntity.class);
    }

    @Override
    protected AffiliationEntity testSerializableObject() {
        return new AffiliationEntityModel(TSID.fast(), "SAMPLEFABRIC", "MNUWRO01");
    }

    @Override
    protected String testDeserializableJSON() {
        return "{\"uid\":%s,\"code\":\"SAMPLEFABRIC\",\"divi\":\"MNUWRO01\"}"
                .formatted(TSID.fast().toLong());
    }
}
