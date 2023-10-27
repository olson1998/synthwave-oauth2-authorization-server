package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthoritiesBinding;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.AuthoritiesBindingsJson.AUTHORITIES_BINDINGS_CHILD_AUTHORITY_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.AuthoritiesBindingsJson.AUTHORITIES_BINDINGS_PARENT_AUTHORITY_JSON_FIELD;

class AuthoritiesBindingsStdSerializer extends AbstractObjectStdSerializer<AuthoritiesBinding> {

    protected AuthoritiesBindingsStdSerializer() {
        super(AuthoritiesBinding.class);
    }

    @Override
    protected void serializeObject(AuthoritiesBinding authoritiesBinding, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(AUTHORITIES_BINDINGS_PARENT_AUTHORITY_JSON_FIELD, authoritiesBinding.getParentAuthority(), jsonGenerator);
        writeField(AUTHORITIES_BINDINGS_CHILD_AUTHORITY_JSON_FIELD, authoritiesBinding.getParentAuthority(), jsonGenerator);
    }

}
