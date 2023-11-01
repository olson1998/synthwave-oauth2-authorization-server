package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PostLoginRedirectURI implements RedirectURI {

    public static final String TYPE = "POST_LOGIN";

    private final String redirectUri;

    @Override
    public boolean isPostLogin() {
        return true;
    }

    @Override
    public boolean isPostLogout() {
        return false;
    }
}
