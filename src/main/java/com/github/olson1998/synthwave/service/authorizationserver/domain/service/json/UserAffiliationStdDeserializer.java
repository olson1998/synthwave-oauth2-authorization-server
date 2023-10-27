package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.UserAffiliationJson;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAffiliation;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;
import io.hypersistence.tsid.TSID;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.UserAffiliationJson.*;

class UserAffiliationStdDeserializer extends AbstractObjectStdDeserializer<UserAffiliation> {

    UserAffiliationStdDeserializer() {
        super(UserAffiliation.class);
    }

    @Override
    protected UserAffiliation deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException{
        var userId = readJsonProperty(USER_AFFILIATION_USER_ID_JSON_FIELD, objectNode, objectCodec, TSID.class, true);
        var code = readJsonProperty(USER_AFFILIATION_CODE_JSON_FIELD, objectNode, objectCodec, String.class, true);
        var divi = readJsonProperty(USER_AFFILIATION_DIVI_JSON_FIELD, objectNode, objectCodec, String.class, true);
        return new UserAffiliationJson(
                userId,
                code,
                divi
        );
    }
}
