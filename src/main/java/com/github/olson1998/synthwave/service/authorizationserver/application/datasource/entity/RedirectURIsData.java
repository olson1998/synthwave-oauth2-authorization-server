package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectUriScope;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.RedirectUrisValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.util.Optional;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectUriScope.POST_LOGIN;
import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectUriScope.POST_LOGOUT;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "REDURI")
public class RedirectURIsData implements RedirectURI, Persistable<RedirectUrisValue> {

    @EmbeddedId
    private RedirectUrisValue redirect;

    public RedirectURIsData(RedirectUriScope scope, String redirectUri) {
        this.redirect = new RedirectUrisValue(scope, redirectUri);
    }

    public RedirectURIsData(String scope, String redirectUri) {
        this.redirect = new RedirectUrisValue(RedirectUriScope.valueOf(scope), redirectUri);
    }

    public RedirectURIsData(@NonNull RedirectURI redirectUri) {
        RedirectUriScope scope = null;
        if(redirectUri.isPostLogin()){
            scope = POST_LOGIN;
        } else if (redirectUri.isPostLogout()) {
            scope = POST_LOGOUT;
        }
        this.redirect = new RedirectUrisValue(scope, redirectUri.getRedirectUri());
    }

    @Override
    public RedirectUrisValue getId() {
        return redirect;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public boolean isPostLogin() {
        return Optional.ofNullable(redirect)
                .map(RedirectUrisValue::getScope)
                .map(scope -> scope == POST_LOGOUT)
                .orElse(false);
    }

    @Override
    public boolean isPostLogout() {
        return Optional.ofNullable(redirect)
                .map(RedirectUrisValue::getScope)
                .map(scope -> scope == POST_LOGIN)
                .orElse(false);
    }

    @Override
    public String getRedirectUri() {
        return Optional.ofNullable(redirect)
                .map(RedirectUrisValue::getUri)
                .orElse(null);
    }

}
