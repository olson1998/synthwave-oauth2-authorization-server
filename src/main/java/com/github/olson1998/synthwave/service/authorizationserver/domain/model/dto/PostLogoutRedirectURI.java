package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class PostLogoutRedirectURI extends RedirectURIDTO {

    public static final String TYPE = "POST_LOGOUT";

    public PostLogoutRedirectURI(String uri) {
        super(uri);
    }

    @Override
    public boolean isPostLogin() {
        return false;
    }

    @Override
    public boolean isPostLogout() {
        return true;
    }

}
