package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user.embbedabble.ParentAuthorityValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ParentAuthority;
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
@Table(name = "PAUDTA")
public class ParentAuthorityData implements ParentAuthority {

    @EmbeddedId
    private ParentAuthorityValue value;

    @Column(name = "PACTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    @Column(name = "PAETMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime expireOn;

    @Column(name = "PAATMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime activeFrom;

    @Override
    public Long getAuthorityId() {
        return Optional.ofNullable(value)
                .map(ParentAuthorityValue::getAuthorityId)
                .orElse(null);
    }

    @Override
    public Long getUpperAuthorityId() {
        return Optional.ofNullable(value)
                .map(ParentAuthorityValue::getUpperAuthorityId)
                .orElse(null);
    }
}
