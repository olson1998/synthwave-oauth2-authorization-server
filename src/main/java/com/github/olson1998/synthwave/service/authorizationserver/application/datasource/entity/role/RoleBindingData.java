package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role.embbedable.RoleBindingValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.RoleBinding;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import com.github.olson1998.synthwave.support.jpa.audit.CreatedOnEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

import java.util.Optional;

import static com.github.olson1998.synthwave.support.jpa.generator.GeneratorConfig.MUTABLE_DATETIME_TIMESTAMP_GENERATOR;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@EntityListeners({CreatedOnEntityListener.class})

@Entity
@Table(name = "ROLEBIND")
public class RoleBindingData implements Persistable<RoleBindingValue>, RoleBinding {

    @EmbeddedId
    private RoleBindingValue value;

    @Column(name = "RBCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    public RoleBindingData(@NonNull RoleBinding roleBinding) {
        this.value = new RoleBindingValue(
                roleBinding.getUserId(),
                roleBinding.getRoleId()
        );
        this.createdOn = roleBinding.getCreatedOn();
    }

    @Override
    public RoleBindingValue getId() {
        return value;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public Long getUserId() {
        return Optional.ofNullable(value)
                .map(RoleBindingValue::getUserId)
                .orElse(null);
    }

    @Override
    public Long getRoleId() {
        return Optional.ofNullable(value)
                .map(RoleBindingValue::getRoleId)
                .orElse(null);
    }
}
