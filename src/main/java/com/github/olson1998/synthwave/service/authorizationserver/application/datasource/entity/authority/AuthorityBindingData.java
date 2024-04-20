package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority.embbedable.AuthorityBindingValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.AuthorityBinding;
import com.github.olson1998.synthwave.support.jpa.audit.CreatedOnEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@EntityListeners({CreatedOnEntityListener.class})

@Entity
@Table(name = "AUTHBIND")
public class AuthorityBindingData implements Persistable<AuthorityBindingValue>, AuthorityBinding {

    @EmbeddedId
    private AuthorityBindingValue value;

    @Column(name = "ABCTMP")
    private MutableDateTime createdOn;

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
