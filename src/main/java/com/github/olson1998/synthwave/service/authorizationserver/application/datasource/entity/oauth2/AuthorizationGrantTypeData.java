package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.AuthorizationGrantTypeJavaType;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.UUID;

public class AuthorizationGrantTypeData {

    private UUID id;

    @JavaType(AuthorizationGrantTypeJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private AuthorizationGrantType value;

    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime cratedOn;
}
