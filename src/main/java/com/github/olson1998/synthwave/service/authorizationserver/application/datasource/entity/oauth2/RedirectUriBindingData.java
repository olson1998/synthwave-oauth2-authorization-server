package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.joda.time.MutableDateTime;

import java.util.UUID;

public class RedirectUriBindingData {

    private Long registeredClientId;

    private Long redirectUriId;

    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;
}
