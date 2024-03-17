package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.emb.ClientAuthenticationMethodBindingValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientAuthenticationMethodBinding;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "OAU2CAMB")
public class ClientAuthenticationMethodBindingData implements ClientAuthenticationMethodBinding {

    @EmbeddedId
    private ClientAuthenticationMethodBindingValue properties;

    @Override
    public Long getRegisteredClientId() {
        return Optional.ofNullable(properties)
                .map(ClientAuthenticationMethodBindingValue::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public Long getClientAuthenticationMethodId() {
        return Optional.ofNullable(properties)
                .map(ClientAuthenticationMethodBindingValue::getClientAuthenticationMethodId)
                .orElse(null);
    }
}
