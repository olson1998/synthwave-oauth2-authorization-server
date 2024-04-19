package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.AuthorizationGrantTypeJavaType;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.AuthorizationGrantTypeEntity;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.AuthorizationGrantTypeData.AUTHORIZATION_GRANT_TYPE_ID_SEQUENCE_GENERATOR;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@SequenceGenerator(name = AUTHORIZATION_GRANT_TYPE_ID_SEQUENCE_GENERATOR, sequenceName = "AGIDSEQ", allocationSize = 1)

@Entity
@Table(name = "OAU2AGT")
public class AuthorizationGrantTypeData implements Persistable<Long>, AuthorizationGrantTypeEntity {

    public static final String AUTHORIZATION_GRANT_TYPE_ID_SEQUENCE_GENERATOR = "AGIDSEQ";

    @Id
    @Column(name = "AGID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = AUTHORIZATION_GRANT_TYPE_ID_SEQUENCE_GENERATOR)
    private Long id;

    @Column(name = "AGVAL")
    @JavaType(AuthorizationGrantTypeJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private AuthorizationGrantType grantType;

    @Column(name = "AGCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    public AuthorizationGrantTypeData(AuthorizationGrantTypeEntity authorizationGrantType) {
        this.id = authorizationGrantType.getId();
        this.grantType = authorizationGrantType.getGrantType();
        this.createdOn = authorizationGrantType.getCreatedOn();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
