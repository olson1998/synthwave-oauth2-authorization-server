package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.RedirectUriJson;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectUri;

class RedirectUriJSONMappingTest extends AuthorizationServerObjectMappingTest<RedirectUri> {

    protected RedirectUriJSONMappingTest() {
        super(RedirectUri.class);
    }

    @Override
    protected RedirectUri testSerializableObject() {
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
