package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.AffiliationEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AffiliationEntity;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import io.hypersistence.tsid.TSID;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.AffiliationEntityModel.*;

class UserAffiliationEntityStdDeserializer extends AbstractObjectStdDeserializer<AffiliationEntity> {

    UserAffiliationEntityStdDeserializer() {
        super(AffiliationEntity.class);
    }

    @Override
    protected AffiliationEntity deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException{
        var userId = readJsonProperty(USER_AFFILIATION_USER_ID_JSON_FIELD, objectNode, objectCodec, TSID.class, true);
        var code = readJsonProperty(USER_AFFILIATION_CODE_JSON_FIELD, objectNode, objectCodec, String.class, true);
        var divi = readJsonProperty(USER_AFFILIATION_DIVI_JSON_FIELD, objectNode, objectCodec, String.class, true);
        return new AffiliationEntityModel(
                userId,
                code,
                divi
        );
    }
}
