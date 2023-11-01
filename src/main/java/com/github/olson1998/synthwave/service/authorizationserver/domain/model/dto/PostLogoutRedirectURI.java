package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PostLogoutRedirectURI implements RedirectURI {

    public static final String TYPE = "POST_LOGOUT";

    private final String redirectUri;

    @Override
    public boolean isPostLogin() {
        return false;
    }

    @Override
    public boolean isPostLogout() {
        return true;
    }

}
