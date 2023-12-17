package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.RedirectClientBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectClientBound;
import io.hypersistence.tsid.TSID;
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
@Table(name = "O2EUCB")
public class RedirectClientBoundData implements Persistable<RedirectClientBinding>, RedirectClientBound {

    @EmbeddedId
    private RedirectClientBinding binding;

    public RedirectClientBoundData(RedirectClientBound redirectClientBound) {
        this.binding = new RedirectClientBinding(redirectClientBound);
    }

    @Override
    public RedirectClientBinding getId() {
        return binding;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public TSID getRegisteredClientId() {
        return Optional.ofNullable(binding)
                .map(RedirectClientBinding::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public TSID getRedirectId() {
        return Optional.ofNullable(binding)
                .map(RedirectClientBinding::getRedirectId)
                .orElse(null);
    }
}
