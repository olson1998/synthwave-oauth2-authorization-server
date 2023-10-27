package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.UserAffiliationJson;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAffiliation;
import io.hypersistence.tsid.TSID;

class UserAffiliationJSONMappingTest extends AuthorizationServerObjectMappingTest<UserAffiliation> {

    UserAffiliationJSONMappingTest() {
        super(UserAffiliation.class);
    }

    @Override
    protected UserAffiliation testSerializableObject() {
        return new UserAffiliationJson(TSID.fast(), "SAMPLEFABRIC", "MNUWRO01");
    }

    @Override
    protected String testDeserializableJSON() {
        return "{\"uid\":%s,\"code\":\"SAMPLEFABRIC\",\"divi\":\"MNUWRO01\"}"
                .formatted(TSID.fast().toLong());
    }
}
