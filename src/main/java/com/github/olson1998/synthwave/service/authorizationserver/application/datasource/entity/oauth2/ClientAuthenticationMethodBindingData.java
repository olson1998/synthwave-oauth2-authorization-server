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
@Table(name = "OA2CMB")
public class ClientAuthenticationMethodBindingData implements ClientAuthenticationMethodBinding {

    @EmbeddedId
    private ClientAuthenticationMethodBindingValue binding;

    @Override
    public Long getRegisteredClientId() {
        return Optional.ofNullable(binding)
                .map(ClientAuthenticationMethodBindingValue::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public Long getClientAuthenticationMethodId() {
        return Optional.ofNullable(binding)
                .map(ClientAuthenticationMethodBindingValue::getClientAuthenticationMethodId)
                .orElse(null);
    }
}
