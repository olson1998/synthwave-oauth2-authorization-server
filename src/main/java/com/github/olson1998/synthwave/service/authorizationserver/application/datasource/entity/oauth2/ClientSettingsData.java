package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.JwsAlgorithmJavaType;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;

import java.util.UUID;

public class ClientSettingsData {

    private Long registeredClientId;

    private Boolean requireProofKey;

    private Boolean requireAuthorizationConsent;

    private String jwkSetUrl;

    @JavaType(JwsAlgorithmJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private JwsAlgorithm jwsAlgorithm;

    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;
}
