package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.exception.UnknownRedirectURITypeException;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PostLoginRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PostLogoutRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;

import java.io.IOException;
import java.util.Optional;

class RedirectURIStdDeserializer extends AbstractObjectStdDeserializer<RedirectURI> {

    RedirectURIStdDeserializer() {
        super(RedirectURI.class);
    }

    @Override
    protected RedirectURI deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
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
        Optional<RedirectURI> redirectUri = Optional.empty();
        switch (type){
            case PostLoginRedirectURI.TYPE -> redirectUri = Optional.of(new PostLoginRedirectURI(uri));
            case PostLogoutRedirectURI.TYPE -> redirectUri = Optional.of(new PostLogoutRedirectURI(uri));
        }
        return redirectUri.orElseThrow(()-> new UnknownRedirectURITypeException(type));
    }
}
