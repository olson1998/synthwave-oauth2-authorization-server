package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.emb.ScopeBindingValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.oauth2.ScopeBinding;
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

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "SCBDTA")
public class ScopeBindingData implements ScopeBinding {

    @EmbeddedId
    private ScopeBindingValue binding;

    @Column(name = "SBCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime cratedOn;

    @Override
    public Long getRegisteredClientId() {
        return Optional.ofNullable(binding)
                .map(ScopeBindingValue::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public Long getScopeId() {
        return Optional.ofNullable(binding)
                .map(ScopeBindingValue::getScopeId)
                .orElse(null);
    }
}
