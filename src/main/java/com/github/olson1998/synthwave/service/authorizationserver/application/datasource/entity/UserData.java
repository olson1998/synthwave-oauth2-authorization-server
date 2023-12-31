package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import com.github.olson1998.synthwave.support.hibernate.javatype.PeriodJavaType;
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
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.MutableDateTime;
import org.joda.time.Period;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "AUEUSR")
public class UserData implements UserEntity, Persistable<TSID> {

    @Id
    @Tsid
    @Column(name = "USRID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID id;

    @Column(name = "USRNME", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "USRENB", nullable = false)
    private Boolean enabled;

    @Column(name = "USREXP")
    @JdbcType(VarcharJdbcType.class)
    @JavaType(MutableDateTimeJavaType.class)
    private MutableDateTime expireDateTime;

    public UserData(@NonNull UserProperties userProperties) {
        this.username = userProperties.getUsername();
        this.enabled = userProperties.getEnabled();
        this.expireDateTime = userProperties.getExpireDateTime();
    }


    @Override
    public boolean isNew() {
        return true;
    }
}
