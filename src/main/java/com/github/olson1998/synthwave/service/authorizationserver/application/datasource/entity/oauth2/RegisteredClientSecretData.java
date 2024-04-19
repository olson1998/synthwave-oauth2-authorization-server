package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientSecret;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RegisteredClientSecretData.REGISTERED_CLIENT_SECRET_ID_SEQUENCE_GENERATOR;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@SequenceGenerator(name = REGISTERED_CLIENT_SECRET_ID_SEQUENCE_GENERATOR, sequenceName = "SCIDSEQ")

@Entity
@Table(name = "OAU2RCLS")
public class RegisteredClientSecretData implements Persistable<Long>, RegisteredClientSecret {

    public static final String REGISTERED_CLIENT_SECRET_ID_SEQUENCE_GENERATOR = "SCIDSEQ";

    @Id
    @Column(name = "SCID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = REGISTERED_CLIENT_SECRET_ID_SEQUENCE_GENERATOR)
    private Long id;

    @Column(name = "RCID")
    private Long registeredClientId;

    @Column(name = "SCVAL")
    private String value;

    @Column(name = "SCCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    @Column(name = "SCETMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime expireOn;

    @Override
    public boolean isNew() {
        return true;
    }
}
