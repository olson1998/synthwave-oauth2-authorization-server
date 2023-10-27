package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.AuthoritiesBindingsJson;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthoritiesBinding;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.AuthoritiesBindingsJson.AUTHORITIES_BINDINGS_CHILD_AUTHORITY_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.AuthoritiesBindingsJson.AUTHORITIES_BINDINGS_PARENT_AUTHORITY_JSON_FIELD;

class AuthoritiesBindingsStdDeserializer extends AbstractObjectStdDeserializer<AuthoritiesBinding> {

    AuthoritiesBindingsStdDeserializer() {
        super(AuthoritiesBinding.class);
    }

    @Override
    protected AuthoritiesBinding deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var parentAuthority = readJsonProperty(
                AUTHORITIES_BINDINGS_PARENT_AUTHORITY_JSON_FIELD,
                objectNode,
                objectCodec,
                String.class
        );
        var childAuthority = readJsonProperty(
                AUTHORITIES_BINDINGS_PARENT_AUTHORITY_JSON_FIELD,
                objectNode,
                objectCodec,
                String.class
        );
        return new AuthoritiesBindingsJson(parentAuthority, childAuthority);
    }

}
