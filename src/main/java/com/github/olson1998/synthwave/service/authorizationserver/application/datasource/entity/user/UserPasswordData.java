package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.joda.time.MutableDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "USERPASS")
public class UserPasswordData implements UserPassword {

    @Id
    @Column(name = "PSID")
    private Long id;

    @Column(name = "USID")
    private Long userId;

    @Column(name = "PSVAL")
    private String value;

    @Column(name = "PSACTV")
    private Boolean active;

    @Column(name = "PSCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    @Column(name = "PSETMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime expireOn;

    public UserPasswordData(UserPassword userPassword) {
        this.id = userPassword.getId();
        this.userId = userPassword.getUserId();
        this.value = userPassword.getValue();
        this.active = userPassword.getActive();
        this.createdOn = userPassword.getCreatedOn();
        this.expireOn = userPassword.getExpireOn();
    }
}
