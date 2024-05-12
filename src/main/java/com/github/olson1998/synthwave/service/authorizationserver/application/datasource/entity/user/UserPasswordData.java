package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import com.github.olson1998.synthwave.support.jpa.audit.CreatedOnEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.java.BooleanJavaType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.hibernate.type.descriptor.jdbc.TinyIntJdbcType;
import org.joda.time.MutableDateTime;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user.UserPasswordData.PASSWORD_ID_SEQUENCE_GENERATOR;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@EntityListeners({CreatedOnEntityListener.class})
@SequenceGenerator(name = PASSWORD_ID_SEQUENCE_GENERATOR, sequenceName = "PSIDSEQ", allocationSize = 1)

@Entity
@Table(name = "USERPASS")
public class UserPasswordData implements UserPassword {

    public static final String PASSWORD_ID_SEQUENCE_GENERATOR = "PSIDSEQ";

    @Id
    @Column(name = "PSID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = PASSWORD_ID_SEQUENCE_GENERATOR)
    private Long id;

    @Column(name = "USID")
    private Long userId;

    @Column(name = "PSVAL")
    private String value;

    @Column(name = "PSACTV")
    @JavaType(BooleanJavaType.class)
    @JdbcType(TinyIntJdbcType.class)
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
