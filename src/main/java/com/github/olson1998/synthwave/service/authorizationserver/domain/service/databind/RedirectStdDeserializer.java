package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RedirectModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.exception.UnknownRedirectURITypeException;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import com.github.olson1998.synthwave.support.web.util.URIModel;

import java.io.IOException;
import java.util.Optional;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RedirectModel.REDIRECT_SCOPE_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RedirectModel.REDIRECT_URI_JSON_FIELD;

class RedirectStdDeserializer extends AbstractObjectStdDeserializer<Redirect> {

    RedirectStdDeserializer() {
        super(Redirect.class);
    }

    @Override
    protected Redirect deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var uri = readJsonProperty(REDIRECT_URI_JSON_FIELD, objectNode, objectCodec, URIModel.class, true);
        var scope = readJsonProperty(REDIRECT_SCOPE_JSON_FIELD, objectNode, objectCodec, RedirectModel.Scope.class, true);
        return new RedirectModel(uri.toModel(), scope);
    }
}
