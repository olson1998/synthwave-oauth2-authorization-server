package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.mapping;

import org.hibernate.HibernateException;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class AuthorizationGrantTypeJavaType extends AbstractJavaType<AuthorizationGrantType> {

    private static final ConcurrentMap<String, AuthorizationGrantType> AUTHORIZATION_GRANT_TYPE_MAP =
            resolveAuthorizationGrantTypeMap();

    protected AuthorizationGrantTypeJavaType() {
        super(AuthorizationGrantType.class);
    }

    @Override
    public <X> X unwrap(AuthorizationGrantType authorizationGrantType, Class<X> aClass, WrapperOptions wrapperOptions) {
        if(authorizationGrantType == null){
            return null;
        }else {
            if(aClass.equals(String.class)){
                return aClass.cast(authorizationGrantType.getValue());
            }else {
                throw unknownUnwrap(aClass);
            }
        }
    }

    @Override
    public <X> AuthorizationGrantType wrap(X x, WrapperOptions wrapperOptions) {
        if(x == null){
            return null;
        }else if(x instanceof String value){
            return Optional.ofNullable(AUTHORIZATION_GRANT_TYPE_MAP.get(value))
                    .orElseThrow(()-> new HibernateException("Unknown authorization grant type: '%s'".formatted(value)));
        }else {
            throw unknownWrap(x.getClass());
        }
    }

    private static ConcurrentMap<String, AuthorizationGrantType> resolveAuthorizationGrantTypeMap(){
        return Stream.of(
                AuthorizationGrantType.AUTHORIZATION_CODE,
                AuthorizationGrantType.REFRESH_TOKEN,
                AuthorizationGrantType.DEVICE_CODE,
                AuthorizationGrantType.JWT_BEARER,
                AuthorizationGrantType.CLIENT_CREDENTIALS
        ).map(authorizationGrantType -> entry(authorizationGrantType.getValue(), authorizationGrantType))
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
