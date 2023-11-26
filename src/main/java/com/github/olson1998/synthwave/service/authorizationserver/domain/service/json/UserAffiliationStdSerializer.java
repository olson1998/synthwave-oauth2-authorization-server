package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.AffiliationModel.USER_AFFILIATION_CODE_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.AffiliationModel.USER_AFFILIATION_DIVI_JSON_FIELD;

class UserAffiliationStdSerializer extends AbstractObjectStdSerializer<Affiliation> {

    protected UserAffiliationStdSerializer() {
        super(Affiliation.class);
    }

    @Override
    protected void serializeObject(Affiliation affiliation, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(USER_AFFILIATION_CODE_JSON_FIELD, affiliation.getCompanyCode(), jsonGenerator);
        writeField(USER_AFFILIATION_DIVI_JSON_FIELD, affiliation.getDivision(), jsonGenerator);
    }
}
