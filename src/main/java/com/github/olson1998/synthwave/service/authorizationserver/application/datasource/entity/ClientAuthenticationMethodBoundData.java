package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.ClientAuthenticationMethodBoundProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.ClientAuthenticationMethodBinding;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "O2ECAM")
public class ClientAuthenticationMethodBoundData implements Persistable<ClientAuthenticationMethodBoundProperties>, ClientAuthenticationMethodBinding {

    @EmbeddedId
    private ClientAuthenticationMethodBoundProperties binding;

    public ClientAuthenticationMethodBoundData(ClientAuthenticationMethodBinding clientAuthenticationMethodBinding) {
        this.binding = new ClientAuthenticationMethodBoundProperties(
                clientAuthenticationMethodBinding.getRegisteredClientId(),
                clientAuthenticationMethodBinding.getClientAuthenticationMethod()
        );
    }

    @Override
    public ClientAuthenticationMethodBoundProperties getId() {
        return binding;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public TSID getRegisteredClientId() {
        return Optional.ofNullable(binding)
                .map(ClientAuthenticationMethodBoundProperties::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public ClientAuthenticationMethod getClientAuthenticationMethod() {
        return Optional.ofNullable(binding)
                .map(ClientAuthenticationMethodBoundProperties::getClientAuthenticationMethod)
                .orElse(null);
    }
}
