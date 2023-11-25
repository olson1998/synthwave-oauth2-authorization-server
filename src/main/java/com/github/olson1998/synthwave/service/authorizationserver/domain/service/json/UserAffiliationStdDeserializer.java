package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.AffiliationModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.AffiliationModel.USER_AFFILIATION_CODE_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.AffiliationModel.USER_AFFILIATION_DIVI_JSON_FIELD;

class UserAffiliationStdDeserializer extends AbstractObjectStdDeserializer<Affiliation> {

    protected UserAffiliationStdDeserializer() {
        super(Affiliation.class);
    }

    @Override
    protected Affiliation deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var code = readJsonProperty(USER_AFFILIATION_CODE_JSON_FIELD, objectNode, objectCodec, String.class, true);
        var divi = readJsonProperty(USER_AFFILIATION_DIVI_JSON_FIELD, objectNode, objectCodec, String.class, true);
        return new AffiliationModel(code, divi);
    }
}
