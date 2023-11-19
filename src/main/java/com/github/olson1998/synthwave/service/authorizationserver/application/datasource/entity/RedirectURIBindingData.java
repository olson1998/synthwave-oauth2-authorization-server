package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.RedirectURIBindingProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURIBinding;
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
@Table(name = "O2ERUB")
public class RedirectURIBindingData implements Persistable<RedirectURIBindingProperties>, RedirectURIBinding {

    @EmbeddedId
    private RedirectURIBindingProperties properties;

    @Override
    public TSID getRedirectURIId() {
        return Optional.ofNullable(properties)
                .map(RedirectURIBindingProperties::getRedirectURIId)
                .orElse(null);
    }

    @Override
    public TSID getRegisteredClientId() {
        return Optional.ofNullable(properties)
                .map(RedirectURIBindingProperties::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public RedirectURIBindingProperties getId() {
        return properties;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
