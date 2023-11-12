package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import lombok.*;

@Data
@AllArgsConstructor
public class PostLogoutRedirectURI implements RedirectURI {

    public static final String TYPE = "POST_LOGOUT";

    private final String uri;

    @Override
    public boolean isPostLogin() {
        return false;
    }

    @Override
    public boolean isPostLogout() {
        return true;
    }

}
