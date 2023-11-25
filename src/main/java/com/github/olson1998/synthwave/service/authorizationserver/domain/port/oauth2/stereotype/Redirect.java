package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

public interface Redirect {

    boolean isPostLogin();

    boolean isPostLogout();

    String getUri();
}
