package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;

class RedirectUriJSONMappingTest extends AuthorizationServerObjectMappingTest<RedirectURI> {

    protected RedirectUriJSONMappingTest() {
        super(RedirectURI.class);
    }

    @Override
    protected RedirectURI testSerializableObject() {
        return new RedirectUriJson(
                RedirectUriJson.Type.POST_LOGIN,
                "http://test/path/to/test"
        );
    }

    @Override
    protected String testDeserializableJSON() {
        return "{\"type\":\"POST_LOGIN\",\"uri\":\"http://test/path/to/test\"}";
    }
}
