package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

public interface RedirectUri {

    boolean isPostLogin();

    boolean isPostLogout();

    String getRedirectUri();
}
