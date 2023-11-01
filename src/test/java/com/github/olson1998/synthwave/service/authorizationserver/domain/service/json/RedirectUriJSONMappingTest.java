package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PostLoginRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PostLogoutRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class RedirectUriJSONMappingTest extends AuthorizationServerObjectMappingTest<RedirectURI> {

    protected RedirectUriJSONMappingTest() {
        super(RedirectURI.class);
    }

    @Override
    protected RedirectURI testSerializableObject() {
        return new PostLoginRedirectURI("http://test/path/to/test");
    }

    @Override
    protected String testDeserializableJSON() {
        return "{\"type\":\"POST_LOGIN\",\"uri\":\"http://test/path/to/test\"}";
    }

    @ParameterizedTest
    @ValueSource(strings = {PostLoginRedirectURI.TYPE, PostLogoutRedirectURI.TYPE})
    void shouldWriteCorrectTypeValue(String type) throws JsonProcessingException {
        var uri = "http://test/path/to/test";
        RedirectURI redirectURI = null;
        switch (type){
            case PostLoginRedirectURI.TYPE -> redirectURI = new PostLoginRedirectURI(uri);
            case PostLogoutRedirectURI.TYPE -> redirectURI = new PostLogoutRedirectURI(uri);
        }
        assertThat(redirectURI).isNotNull();
        var json = objectMapper.writeValueAsString(redirectURI);
        var jsonType = JsonPath.read(json, "$['type']").toString();
        assertThat(jsonType).isEqualTo(type);
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"type\":\"POST_LOGIN\",\"uri\":\"http://test/path/to/test\"}", "{\"type\":\"POST_LOGOUT\",\"uri\":\"http://test/path/to/test\"}"})
    void shouldDeserializeRedirectURIAsCorrectInstance(String json) throws JsonProcessingException {
        Class<? extends RedirectURI> expectedRedirectUriClass = null;
        switch (json){
            case "{\"type\":\"POST_LOGIN\",\"uri\":\"http://test/path/to/test\"}" -> expectedRedirectUriClass =PostLoginRedirectURI.class;
            case "{\"type\":\"POST_LOGOUT\",\"uri\":\"http://test/path/to/test\"}" -> expectedRedirectUriClass = PostLogoutRedirectURI.class;
        }
        assertThat(expectedRedirectUriClass).isNotNull();
        var redirectURI = objectMapper.readValue(json, RedirectURI.class);
        assertThat(redirectURI).isInstanceOf(expectedRedirectUriClass);
    }
}
