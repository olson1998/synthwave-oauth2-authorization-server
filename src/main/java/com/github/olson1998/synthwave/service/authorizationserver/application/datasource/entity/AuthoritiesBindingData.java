package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.AuthoritiesBindingValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthoritiesBinding;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "ATHBND")
public class AuthoritiesBindingData implements AuthoritiesBinding, Persistable<AuthoritiesBindingValue> {

    @EmbeddedId
    private AuthoritiesBindingValue binding;

    public AuthoritiesBindingData(String parentAuthority, String childAuthority) {
        this.binding = new AuthoritiesBindingValue(parentAuthority, childAuthority);
    }

    public AuthoritiesBindingData(AuthoritiesBinding authoritiesBinding) {
        this.binding = new AuthoritiesBindingValue(
                authoritiesBinding.getParentAuthority(),
                authoritiesBinding.getChildAuthority()
        );
    }

    @Override
    public AuthoritiesBindingValue getId() {
        return binding;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public String getParentAuthority() {
        return Optional.ofNullable(binding)
                .map(AuthoritiesBindingValue::getParentAuthority)
                .orElse(null);
    }

    @Override
    public String getChildAuthority() {
        return Optional.ofNullable(binding)
                .map(AuthoritiesBindingValue::getChildAuthority)
                .orElse(null);
    }
}
