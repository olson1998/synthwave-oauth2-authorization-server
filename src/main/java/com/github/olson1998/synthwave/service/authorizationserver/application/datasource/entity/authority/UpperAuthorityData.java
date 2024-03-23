package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority.embbedabble.UpperAuthorityValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.UpperAuthority;
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
@Table(name = "AUTHUPPR")
public class UpperAuthorityData implements Persistable<UpperAuthorityValue>, UpperAuthority {

    @EmbeddedId
    private UpperAuthorityValue value;

    @Override
    public Long getAuthorityId() {
        return Optional.ofNullable(value)
                .map(UpperAuthorityValue::getAuthorityId)
                .orElse(null);
    }

    @Override
    public Long getUpperAuthorityId() {
        return Optional.ofNullable(value)
                .map(UpperAuthorityValue::getUpperAuthorityId)
                .orElse(null);
    }

    @Override
    public UpperAuthorityValue getId() {
        return value;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
