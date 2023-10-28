package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

public interface RedirectURI {

    boolean isPostLogin();

    boolean isPostLogout();

    String getRedirectUri();
}
