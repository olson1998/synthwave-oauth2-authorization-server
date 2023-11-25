package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostLogoutRedirect extends RedirectModel {

    public static final String TYPE = "POST_LOGOUT";

    public PostLogoutRedirect(String uri) {
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
