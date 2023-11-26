package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.RedirectBoundProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectBound;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
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
public class RedirectBoundData implements Persistable<RedirectBoundProperties>, RedirectBound {

    @EmbeddedId
    private RedirectBoundProperties properties;

    public RedirectBoundData(RedirectBound redirectBound) {
        this.properties = new RedirectBoundProperties(redirectBound);
    }

    @Override
    public TSID getRedirectId() {
        return Optional.ofNullable(properties)
                .map(RedirectBoundProperties::getRedirectId)
                .orElse(null);
    }

    @Override
    public Affiliation getAffiliation() {
        return Optional.ofNullable(properties)
                .map(RedirectBoundProperties::getAffiliationProperties)
                .orElse(null);
    }

    @Override
    public RedirectBoundProperties getId() {
        return properties;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
