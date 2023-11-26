package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.exception.UnknownRedirectURITypeException;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLoginRedirect;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLogoutRedirect;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;

import java.io.IOException;
import java.util.Optional;

class RedirectStdDeserializer extends AbstractObjectStdDeserializer<Redirect> {

    RedirectStdDeserializer() {
        super(Redirect.class);
    }

    @Override
    protected Redirect deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var type = readJsonProperty(
                "type",
                objectNode,
                objectCodec,
                String.class,
                true
        );
        var uri = readJsonProperty(
                "uri",
                objectNode,
                objectCodec,
                String.class,
                true
        );
        Optional<Redirect> redirectUri = Optional.empty();
        switch (type){
            case PostLoginRedirect.TYPE -> redirectUri = Optional.of(new PostLoginRedirect(uri));
            case PostLogoutRedirect.TYPE -> redirectUri = Optional.of(new PostLogoutRedirect(uri));
        }
        return redirectUri.orElseThrow(()-> new UnknownRedirectURITypeException(type));
    }
}
