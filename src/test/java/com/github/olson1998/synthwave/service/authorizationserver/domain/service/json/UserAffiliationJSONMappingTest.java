package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserAffiliationEntityDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAffiliationEntity;
import io.hypersistence.tsid.TSID;

class UserAffiliationJSONMappingTest extends AuthorizationServerObjectMappingTest<UserAffiliationEntity> {

    UserAffiliationJSONMappingTest() {
        super(UserAffiliationEntity.class);
    }

    @Override
    protected UserAffiliationEntity testSerializableObject() {
        return new UserAffiliationEntityDTO(TSID.fast(), "SAMPLEFABRIC", "MNUWRO01");
    }

    @Override
    protected String testDeserializableJSON() {
        return "{\"uid\":%s,\"code\":\"SAMPLEFABRIC\",\"divi\":\"MNUWRO01\"}"
                .formatted(TSID.fast().toLong());
    }
}
