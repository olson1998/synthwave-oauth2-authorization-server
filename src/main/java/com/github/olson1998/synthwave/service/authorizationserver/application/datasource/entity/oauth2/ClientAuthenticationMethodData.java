package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.ClientAuthenticationMethodJavaType;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientAuthenticationMethodEntity;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import com.github.olson1998.synthwave.support.jpa.audit.CreatedOnEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ClientAuthenticationMethodData.CLIENT_AUTHENTICATION_METHOD_ID_SEQUENCE_GENERATOR;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@EntityListeners({CreatedOnEntityListener.class})
@SequenceGenerator(name = CLIENT_AUTHENTICATION_METHOD_ID_SEQUENCE_GENERATOR, sequenceName = "CMIDSEQ", allocationSize = 1)

@Entity
@Table(name = "OAU2CAM")
public class ClientAuthenticationMethodData implements ClientAuthenticationMethodEntity {

    public static final String CLIENT_AUTHENTICATION_METHOD_ID_SEQUENCE_GENERATOR = "CMIDSEQ";

    @Id
    @Column(name = "CMID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = CLIENT_AUTHENTICATION_METHOD_ID_SEQUENCE_GENERATOR)
    private Long id;

    @Column(name = "CMVAL")
    @JavaType(ClientAuthenticationMethodJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private ClientAuthenticationMethod method;

    @Column(name = "CMCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    public ClientAuthenticationMethodData(ClientAuthenticationMethodEntity clientAuthenticationMethodEntity) {
        this.id = clientAuthenticationMethodEntity.getId();
        this.method = clientAuthenticationMethodEntity.getMethod();
        this.createdOn = clientAuthenticationMethodEntity.getCreatedOn();
    }
}
