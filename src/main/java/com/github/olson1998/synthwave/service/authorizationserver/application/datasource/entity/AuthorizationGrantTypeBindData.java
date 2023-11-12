package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.AuthorizationGrantTypeBind;
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
@Table(name = "RCLTGT")
public class AuthorizationGrantTypeBindData implements Persistable<AuthorizationGrantTypeBind>, AuthorizationGrantTypeBinding {

    @EmbeddedId
    private AuthorizationGrantTypeBind binding;

    public AuthorizationGrantTypeBindData(AuthorizationGrantTypeBinding authorizationGrantTypeBinding) {
        this.binding = new AuthorizationGrantTypeBind(
                authorizationGrantTypeBinding.getRegisteredClientId(),
                authorizationGrantTypeBinding.getAuthorizationGrantType()
        );
    }

    @Override
    public AuthorizationGrantTypeBind getId() {
        return binding;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public TSID getRegisteredClientId() {
        return Optional.ofNullable(binding)
                .map(AuthorizationGrantTypeBind::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public AuthorizationGrantType getAuthorizationGrantType() {
        return Optional.ofNullable(binding)
                .map(AuthorizationGrantTypeBind::getAuthorizationGrantType)
                .orElse(null);
    }
}
