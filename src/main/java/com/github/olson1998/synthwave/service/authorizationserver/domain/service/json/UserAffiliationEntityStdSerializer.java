package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AffiliationEntity;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.AffiliationEntityModel.*;

class UserAffiliationEntityStdSerializer extends AbstractObjectStdSerializer<AffiliationEntity> {

    UserAffiliationEntityStdSerializer() {
        super(AffiliationEntity.class);
    }

    @Override
    protected void serializeObject(AffiliationEntity affiliation, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(USER_AFFILIATION_USER_ID_JSON_FIELD, affiliation.getUserId(), jsonGenerator);
        writeField(USER_AFFILIATION_CODE_JSON_FIELD, affiliation.getCompanyCode(), jsonGenerator);
        writeField(USER_AFFILIATION_DIVI_JSON_FIELD, affiliation.getDivision(), jsonGenerator);
    }
}
