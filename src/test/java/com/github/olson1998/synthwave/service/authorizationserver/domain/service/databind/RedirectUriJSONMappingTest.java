package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class RedirectUriJSONMappingTest extends AuthorizationServerObjectMappingTest<Redirect> {

    protected RedirectUriJSONMappingTest() {
        super(Redirect.class);
    }

    @Override
    protected Redirect testSerializableObject() {
        return new PostLoginRedirect("http://test/path/to/test");
    }

    @Override
    protected String testDeserializableJSON() {
        return "{\"type\":\"POST_LOGIN\",\"uri\":\"http://test/path/to/test\"}";
    }

    @ParameterizedTest
    @ValueSource(strings = {PostLoginRedirect.TYPE, PostLogoutRedirect.TYPE})
    void shouldWriteCorrectTypeValue(String type) throws JsonProcessingException {
        var uri = "http://test/path/to/test";
        Redirect redirect = null;
        switch (type){
            case PostLoginRedirect.TYPE -> redirect = new PostLoginRedirect(uri);
            case PostLogoutRedirect.TYPE -> redirect = new PostLogoutRedirect(uri);
        }
        assertThat(redirect).isNotNull();
        var json = objectMapper.writeValueAsString(redirect);
        var jsonType = JsonPath.read(json, "$['type']").toString();
        assertThat(jsonType).isEqualTo(type);
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"type\":\"POST_LOGIN\",\"uri\":\"http://test/path/to/test\"}", "{\"type\":\"POST_LOGOUT\",\"uri\":\"http://test/path/to/test\"}"})
    void shouldDeserializeRedirectURIAsCorrectInstance(String json) throws JsonProcessingException {
        Class<? extends Redirect> expectedRedirectUriClass = null;
        switch (json){
            case "{\"type\":\"POST_LOGIN\",\"uri\":\"http://test/path/to/test\"}" -> expectedRedirectUriClass = PostLoginRedirect.class;
            case "{\"type\":\"POST_LOGOUT\",\"uri\":\"http://test/path/to/test\"}" -> expectedRedirectUriClass = PostLogoutRedirect.class;
        }
        assertThat(expectedRedirectUriClass).isNotNull();
        var redirectURI = objectMapper.readValue(json, Redirect.class);
        assertThat(redirectURI).isInstanceOf(expectedRedirectUriClass);
    }
}
