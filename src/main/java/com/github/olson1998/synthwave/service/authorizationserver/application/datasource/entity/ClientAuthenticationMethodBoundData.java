package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.ClientAuthenticationMethodBinding;
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
public class ClientAuthenticationMethodBoundData implements Persistable<ClientAuthenticationMethodBinding>, com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.ClientAuthenticationMethodBinding {

    @EmbeddedId
    private ClientAuthenticationMethodBinding binding;

    public ClientAuthenticationMethodBoundData(com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.ClientAuthenticationMethodBinding clientAuthenticationMethodBinding) {
        this.binding = new ClientAuthenticationMethodBinding(
                clientAuthenticationMethodBinding.getRegisteredClientId(),
                clientAuthenticationMethodBinding.getClientAuthenticationMethod()
        );
    }

    @Override
    public ClientAuthenticationMethodBinding getId() {
        return binding;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public TSID getRegisteredClientId() {
        return Optional.ofNullable(binding)
                .map(ClientAuthenticationMethodBinding::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public ClientAuthenticationMethod getClientAuthenticationMethod() {
        return Optional.ofNullable(binding)
                .map(ClientAuthenticationMethodBinding::getClientAuthenticationMethod)
                .orElse(null);
    }
}
