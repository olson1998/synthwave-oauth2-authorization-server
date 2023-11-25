package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostLoginRedirect extends RedirectModel {

    public static final String TYPE = "POST_LOGIN";

    public PostLoginRedirect(String uri) {
        super(uri);
    }

    @Override
    public boolean isPostLogin() {
        return true;
    }

    @Override
    public boolean isPostLogout() {
        return false;
    }
}
