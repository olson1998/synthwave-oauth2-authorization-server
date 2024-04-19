package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientProperties;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RegisteredClientData.REGISTERED_CLIENT_ID_SEQUENCE_GENERATOR;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@SequenceGenerator(name = REGISTERED_CLIENT_ID_SEQUENCE_GENERATOR, sequenceName = "RCIDSEQ", allocationSize = 1)

@Entity
@Table(name = "OAU2RCL")
public class RegisteredClientData implements Persistable<Long>, RegisteredClientProperties {

    public static final String REGISTERED_CLIENT_ID_SEQUENCE_GENERATOR = "RCIDSEQ";

    @Id
    @Column(name = "RCID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = REGISTERED_CLIENT_ID_SEQUENCE_GENERATOR)
    private Long id;

    @Column(name = "RCCID")
    private String clientId;

    @Column(name = "RCNAME")
    private String name;

    @Column(name = "RCCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    @Column(name = "RCETMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime expireOn;

    @Column(name = "RCATMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime activeFrom;

    public RegisteredClientData(@NonNull RegisteredClientProperties registeredClientProperties) {
        this.id = registeredClientProperties.getId();
        this.clientId = registeredClientProperties.getClientId();
        this.name = registeredClientProperties.getName();
        this.createdOn = registeredClientProperties.getCreatedOn();
        this.expireOn = registeredClientProperties.getExpireOn();
        this.activeFrom = registeredClientProperties.getActiveFrom();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
