package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.lang.reflect.Type;

public class AuthorizationGrantTypeJavaType extends AbstractJavaType<AuthorizationGrantType> {

    protected AuthorizationGrantTypeJavaType() {
        super(AuthorizationGrantType.class);
    }

    @Override
    public <X> X unwrap(AuthorizationGrantType value, Class<X> type, WrapperOptions options) {
        if(value != null) {
            if(type.equals(String.class)) {
                return type.cast(value.getValue());
            } else {
                throw unknownUnwrap(type);
            }
        } else {
            return null;
        }
    }

    @Override
    public <X> AuthorizationGrantType wrap(X value, WrapperOptions options) {
        if(value == null) {
            return null;
        } else {
            if(value instanceof String string) {
                return new AuthorizationGrantType(string);
            } else {
                throw unknownWrap(value.getClass());
            }
        }
    }
}
