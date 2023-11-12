package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

public interface RedirectURI {

    boolean isPostLogin();

    boolean isPostLogout();

    String getUri();
}
