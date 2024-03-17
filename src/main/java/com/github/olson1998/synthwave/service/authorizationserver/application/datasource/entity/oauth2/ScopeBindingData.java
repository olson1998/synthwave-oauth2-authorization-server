package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.emb.ScopeBindingValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ScopeBinding;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "OAU2SCPB")
public class ScopeBindingData implements Persistable<ScopeBindingValue>, ScopeBinding {

    @EmbeddedId
    private ScopeBindingValue properties;

    @Column(name = "SBCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    @Override
    public Long getRegisteredClientId() {
        return Optional.ofNullable(properties)
                .map(ScopeBindingValue::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public Long getScopeId() {
        return Optional.ofNullable(properties)
                .map(ScopeBindingValue::getScopeId)
                .orElse(null);
    }

    @Override
    public ScopeBindingValue getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
