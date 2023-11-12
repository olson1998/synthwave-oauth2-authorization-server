package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.mapping;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Optional;

public class ClientAuthenticationMethodJavaType extends AbstractJavaType<ClientAuthenticationMethod> {

    public ClientAuthenticationMethodJavaType() {
        super(ClientAuthenticationMethod.class);
    }

    @Override
    public <X> X unwrap(ClientAuthenticationMethod clientAuthenticationMethod, Class<X> aClass, WrapperOptions wrapperOptions) {
        if(aClass.isAssignableFrom(String.class)){
            return aClass.cast(clientAuthenticationMethod.getValue());
        } else if (aClass.isAssignableFrom(byte[].class)) {
            return aClass.cast(clientAuthenticationMethod.getValue().getBytes());
        }else {
            throw unknownUnwrap(aClass);
        }
    }

    @Override
    public <X> ClientAuthenticationMethod wrap(X x, WrapperOptions wrapperOptions) {
        if(x == null){
            return null;
        } else if (x instanceof String str) {
            return new ClientAuthenticationMethod(str);
        } else if (x instanceof byte[] byteArray) {
            return new ClientAuthenticationMethod(new String(byteArray));
        }else {
            throw unknownWrap(x.getClass());
        }
    }
}
