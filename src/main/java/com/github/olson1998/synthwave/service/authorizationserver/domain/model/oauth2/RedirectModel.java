package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class RedirectModel implements Redirect {

    public static final String REDIRECT_URI_JSON_FIELD = "uri";

    public static final String REDIRECT_SCOPE_JSON_FIELD = "scope";

    private final String uri;

    private final Scope scope;

    @Override
    public boolean isPostLogin() {
        return scope == Scope.POST_LOGIN;
    }

    @Override
    public boolean isPostLogout() {
        return scope ==Scope.POST_LOGOUT;
    }

    public enum Scope{

        POST_LOGIN,
        POST_LOGOUT;

        public static Scope resolveScope(Redirect redirect){
            if(redirect.isPostLogin()){
                return POST_LOGIN;
            } else if (redirect.isPostLogout()) {
                return POST_LOGOUT;
            }else {
                return null;
            }
        }
    }

}
