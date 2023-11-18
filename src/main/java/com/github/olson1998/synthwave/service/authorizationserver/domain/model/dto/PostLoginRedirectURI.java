package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class PostLoginRedirectURI extends RedirectURIDTO {

    public static final String TYPE = "POST_LOGIN";

    public PostLoginRedirectURI(String uri) {
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
