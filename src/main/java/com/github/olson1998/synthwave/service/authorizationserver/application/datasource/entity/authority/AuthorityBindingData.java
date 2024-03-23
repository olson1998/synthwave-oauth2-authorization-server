package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority.embbedabble.AuthorityBindingValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.AuthorityBinding;
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
@Table(name = "AUTHBIND")
public class AuthorityBindingData implements Persistable<AuthorityBindingValue>, AuthorityBinding {

    @EmbeddedId
    private AuthorityBindingValue value;

    @Override
    public AuthorityBindingValue getId() {
        return value;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public Long getUserId() {
        return Optional.ofNullable(value)
                .map(AuthorityBindingValue::getUserId)
                .orElse(null);
    }

    @Override
    public Long getAuthorityId() {
        return Optional.ofNullable(value)
                .map(AuthorityBindingValue::getAuthorityId)
                .orElse(null);
    }

    public AuthorityBindingData(AuthorityBinding authorityBinding) {
        this.value = new AuthorityBindingValue(
                authorityBinding.getUserId(),
                authorityBinding.getAuthorityId()
        );
    }

}
