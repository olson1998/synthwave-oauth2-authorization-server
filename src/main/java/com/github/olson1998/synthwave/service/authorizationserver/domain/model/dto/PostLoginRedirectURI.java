package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import lombok.*;

@Data
@AllArgsConstructor
public class PostLoginRedirectURI implements RedirectURI {

    public static final String TYPE = "POST_LOGIN";

    private final String uri;

    @Override
    public boolean isPostLogin() {
        return true;
    }

    @Override
    public boolean isPostLogout() {
        return false;
    }
}
