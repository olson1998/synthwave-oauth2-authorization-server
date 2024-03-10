package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.ClientAuthenticationMethodJavaType;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.oauth2.ClientAuthenticationMethodWrapper;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "CAMDTA")
public class ClientAuthenticationMethodData implements ClientAuthenticationMethodWrapper {

    @Id
    @Column(name = "CMID")
    private Long id;

    @Column(name = "CMVAL")
    @JavaType(ClientAuthenticationMethodJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private ClientAuthenticationMethod method;

    @Column(name = "CMCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime cratedOn;
}
