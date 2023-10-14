package com.github.olson1998.synthwave.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.authorizationserver.domain.port.oauth2.datasource.stereotype.Password;
import com.github.olson1998.synthwave.support.data.hibernate.TSIDJavaType;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "PSSDTA")
public class UserPasswordData implements Persistable<TSID>, Password {

    @Id
    @Column(name = "PSSID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID id;

    @Column(name = "UID", nullable = false)
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID userId;

    @Column(name = "PSSVAL", nullable = false)
    private String value;

    @Column(name = "PSSVER", nullable = false)
    private Boolean latestVersion;

    @Override
    public boolean isNew() {
        return true;
    }
}
