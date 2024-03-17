package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.emb.AuthorizationGrantTypeBindingValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.AuthorizationGrantTypeBinding;
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
@Table(name = "OA2AGB")
public class AuthorizationGrantTypeBindingData implements AuthorizationGrantTypeBinding {

    @EmbeddedId
    private AuthorizationGrantTypeBindingValue binding;

    @Override
    public Long getRegisteredClientId() {
        return Optional.ofNullable(binding)
                .map(AuthorizationGrantTypeBindingValue::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public Long getAuthorizationGrantTypeId() {
        return Optional.ofNullable(binding)
                .map(AuthorizationGrantTypeBindingValue::getAuthorizationGrantTypeId)
                .orElse(null);
    }
}
