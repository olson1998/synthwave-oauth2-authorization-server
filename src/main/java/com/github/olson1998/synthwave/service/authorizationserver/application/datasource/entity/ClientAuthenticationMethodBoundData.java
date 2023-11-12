package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.ClientAuthenticationMethodBind;
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
@Table(name = "RCLTAM")
public class ClientAuthenticationMethodBoundData implements Persistable<ClientAuthenticationMethodBind>, ClientAuthenticationMethodBinding {

    @EmbeddedId
    private ClientAuthenticationMethodBind binding;

    public ClientAuthenticationMethodBoundData(ClientAuthenticationMethodBinding clientAuthenticationMethodBinding) {
        this.binding = new ClientAuthenticationMethodBind(
                clientAuthenticationMethodBinding.getRegisteredClientId(),
                clientAuthenticationMethodBinding.getClientAuthenticationMethod()
        );
    }

    @Override
    public ClientAuthenticationMethodBind getId() {
        return binding;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public TSID getRegisteredClientId() {
        return Optional.ofNullable(binding)
                .map(ClientAuthenticationMethodBind::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public ClientAuthenticationMethod getClientAuthenticationMethod() {
        return Optional.ofNullable(binding)
                .map(ClientAuthenticationMethodBind::getClientAuthenticationMethod)
                .orElse(null);
    }
}
