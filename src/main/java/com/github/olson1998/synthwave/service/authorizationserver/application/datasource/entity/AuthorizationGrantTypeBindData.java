package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.AuthorizationGrantTypeBindingProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthorizationGrantTypeBinding;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "O2EAGB")
public class AuthorizationGrantTypeBindData implements Persistable<AuthorizationGrantTypeBindingProperties>, AuthorizationGrantTypeBinding {

    @EmbeddedId
    private AuthorizationGrantTypeBindingProperties binding;

    public AuthorizationGrantTypeBindData(AuthorizationGrantTypeBinding authorizationGrantTypeBinding) {
        this.binding = new AuthorizationGrantTypeBindingProperties(
                authorizationGrantTypeBinding.getRegisteredClientId(),
                authorizationGrantTypeBinding.getAuthorizationGrantType()
        );
    }

    @Override
    public AuthorizationGrantTypeBindingProperties getId() {
        return binding;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public TSID getRegisteredClientId() {
        return Optional.ofNullable(binding)
                .map(AuthorizationGrantTypeBindingProperties::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public AuthorizationGrantType getAuthorizationGrantType() {
        return Optional.ofNullable(binding)
                .map(AuthorizationGrantTypeBindingProperties::getAuthorizationGrantType)
                .orElse(null);
    }
}
