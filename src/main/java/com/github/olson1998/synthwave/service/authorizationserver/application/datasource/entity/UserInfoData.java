package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.UserGender;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserInfoEntity;
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

import java.time.ZoneId;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "AUEINF")
public class UserInfoData implements UserInfoEntity {

    @Id
    @Column(name = "USRID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID userId;

    @Column(name = "INFNM")
    private String name;

    @Column(name = "INFMNM")
    private String middleName;

    @Column(name = "INFGNM")
    private String givenName;

    @Column(name = "INFGEN")
    private UserGender userGender;

    @Column(name = "INFZID")
    private ZoneId userZoneId;


}
