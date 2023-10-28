package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectUriScope;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class RedirectUrisValue implements Serializable {

    private static final long serialVersionUID = -7539401279039610339L;

    @Column(name = "URISCP")
    @Enumerated(EnumType.STRING)
    private RedirectUriScope scope;

    @Column(name = "URIVAL")
    private String uri;

    public RedirectUrisValue(@NonNull RedirectURI redirectUri) {
        this.uri = redirectUri.getRedirectUri();
        if(redirectUri.isPostLogin()){
            this.scope = RedirectUriScope.POST_LOGIN;
        } else if (redirectUri.isPostLogout()) {
            this.scope = RedirectUriScope.POST_LOGOUT;
        }
    }
}
