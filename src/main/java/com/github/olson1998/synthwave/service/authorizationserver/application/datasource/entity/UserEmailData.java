package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEmailEntity;
import com.github.olson1998.synthwave.support.hibernate.javatype.TSIDJavaType;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "AUEUEM")
public class UserEmailData implements UserEmailEntity {

    @Id
    @Column(name = "USRID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID userId;

    @Column(name = "UEMADD")
    private String emailAddress;

    @Column(name = "UEMVER")
    private Boolean verified;
}
