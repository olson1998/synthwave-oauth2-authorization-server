package com.github.olson1998.synthwave.service.authorizationserver.domain.model.json;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectUri;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class RedirectUriJson implements RedirectUri {

    public static final String REDIRECT_URI_TYPE_JSON_FIELD = "type";

    public static final String REDIRECT_URI_VALUE_JSON_FIELD = "uri";

    private Type type;

    private final String redirectUri;

    @Override
    public boolean isPostLogin() {
        return type == Type.POST_LOGIN;
    }

    @Override
    public boolean isPostLogout() {
        return type == Type.POST_LOGOUT;
    }

    public enum Type{
        POST_LOGIN,
        POST_LOGOUT
    }
}
