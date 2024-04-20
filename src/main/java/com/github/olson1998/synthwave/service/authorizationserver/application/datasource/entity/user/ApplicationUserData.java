package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import com.github.olson1998.synthwave.support.jpa.audit.CreatedOnEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.joda.time.MutableDateTime;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user.ApplicationUserData.USER_ID_SEQUENCE_GENERATOR;
import static com.github.olson1998.synthwave.support.jpa.generator.GeneratorConfig.MUTABLE_DATETIME_TIMESTAMP_GENERATOR;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@EntityListeners({CreatedOnEntityListener.class})
@SequenceGenerator(name = USER_ID_SEQUENCE_GENERATOR, sequenceName = "USIDSEQ", allocationSize = 1)

@Entity
@Table(name = "USERDATA")
public class ApplicationUserData implements ApplicationUser {

    public static final String USER_ID_SEQUENCE_GENERATOR = "USIDSEQ";

    @Id
    @Column(name = "USID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = USER_ID_SEQUENCE_GENERATOR)
    private Long id;

    @Column(name = "USNAME")
    private String username;

    @Column(name = "USCODE")
    private String companyCode;

    @Column(name = "USDIVI")
    private String division;

    @Column(name = "USDSNM")
    private String displayName;

    @Column(name = "USCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    @Column(name = "USETMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime expireOn;
}
