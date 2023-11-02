package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserAffiliation;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserAffiliationDTO.USER_AFFILIATION_CODE_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserAffiliationDTO.USER_AFFILIATION_DIVI_JSON_FIELD;

class UserAffiliationStdSerializer extends AbstractObjectStdSerializer<UserAffiliation> {

    protected UserAffiliationStdSerializer() {
        super(UserAffiliation.class);
    }

    @Override
    protected void serializeObject(UserAffiliation userAffiliation, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(USER_AFFILIATION_CODE_JSON_FIELD, userAffiliation.getCompanyCode(), jsonGenerator);
        writeField(USER_AFFILIATION_DIVI_JSON_FIELD, userAffiliation.getDivision(), jsonGenerator);
    }
}
