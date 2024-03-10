package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.lang.reflect.Type;

public class AuthorizationGrantTypeJavaType extends AbstractJavaType<AuthorizationGrantType> {

    protected AuthorizationGrantTypeJavaType(Type type) {
        super(type);
    }

    @Override
    public <X> X unwrap(AuthorizationGrantType value, Class<X> type, WrapperOptions options) {
        return null;
    }

    @Override
    public <X> AuthorizationGrantType wrap(X value, WrapperOptions options) {
        return null;
    }
}
