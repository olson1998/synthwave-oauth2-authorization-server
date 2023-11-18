package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class RedirectURIDTO implements RedirectURI {

    private final String uri;

    public abstract boolean isPostLogin();

    public abstract boolean isPostLogout();

    public static RedirectURI ofType(String uri, Class<? extends RedirectURI> redirectURIClass){
        if(redirectURIClass.equals(PostLoginRedirectURI.class)){
            return new PostLoginRedirectURI(uri);
        } else if (redirectURIClass.equals(PostLogoutRedirectURI.class)) {
            return new PostLogoutRedirectURI(uri);
        }else {
            throw new IllegalArgumentException("");
        }
    }
}
