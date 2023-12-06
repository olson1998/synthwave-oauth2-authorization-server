package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserPassword;
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

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "AUEPSS")
public class UserPasswordData implements PasswordEntity, Persistable<TSID> {

    @Id
    @Tsid
    @Column(name = "PSSID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID id;

    @Column(name = "USRID", nullable = false)
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID userId;

    @Column(name = "PSSVAL", nullable = false)
    private String value;

    @Column(name = "PSSEXP")
    @JdbcType(VarcharJdbcType.class)
    @JavaType(MutableDateTimeJavaType.class)
    private MutableDateTime expireDateTime;

    public UserPasswordData(@NonNull UserPassword userPassword) {
        this.userId = userPassword.getUserId();
        this.value = userPassword.getValue();
        this.expireDateTime = userPassword.getExpireDateTime();
    }

    @Override
    public boolean isNew() {
        return true;
    }

}
