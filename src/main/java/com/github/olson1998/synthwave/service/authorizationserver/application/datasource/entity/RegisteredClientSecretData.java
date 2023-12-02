package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientSecretEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientSecret;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import com.github.olson1998.synthwave.support.hibernate.javatype.TSIDJavaType;
import io.hypersistence.tsid.TSID;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "O2ESEC")
public class RegisteredClientSecretData implements Persistable<TSID>, RegisteredClientSecretEntity {

    @Id
    @Tsid
    @Column(name = "RCSID")
    @JdbcType(BigIntJdbcType.class)
    @JavaType(TSIDJavaType.class)
    private TSID id;

    @Column(name = "RCLID")
    @JdbcType(BigIntJdbcType.class)
    @JavaType(TSIDJavaType.class)
    private TSID registeredClientId;

    @Column(name = "RCSVAL")
    private String value;

    @Column(name = "RCSEXP")
    @JdbcType(VarcharJdbcType.class)
    @JavaType(MutableDateTimeJavaType.class)
    private MutableDateTime expiresDateTime;

    public RegisteredClientSecretData(@NonNull RegisteredClientSecret registeredClientSecret) {
        this.registeredClientId = registeredClientSecret.getRegisteredClientId();
        this.value = registeredClientSecret.getValue();
        this.expiresDateTime = registeredClientSecret.getExpiresDateTime();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
