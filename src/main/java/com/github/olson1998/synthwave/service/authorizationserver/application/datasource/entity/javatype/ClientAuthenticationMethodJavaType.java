package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Optional;

public class ClientAuthenticationMethodJavaType extends AbstractJavaType<ClientAuthenticationMethod> {

    protected ClientAuthenticationMethodJavaType() {
        super(ClientAuthenticationMethod.class);
    }

    @Override
    public <X> X unwrap(ClientAuthenticationMethod value, Class<X> type, WrapperOptions options) {
        return Optional.ofNullable(value)
                .filter(clientAuthenticationMethod -> {
                    if(!type.equals(String.class)){
                        throw unknownUnwrap(type);
                    }
                    return true;
                })
                .map(ClientAuthenticationMethod::getValue)
                .map(type::cast)
                .orElse(null);
    }

    @Override
    public <X> ClientAuthenticationMethod wrap(X value, WrapperOptions options) {
        return Optional.ofNullable(value)
                .map(x -> {
                    if(x instanceof String string){
                        return string;
                    }else {
                        throw unknownWrap(x.getClass());
                    }
                })
                .map(ClientAuthenticationMethod::new)
                .orElse(null);
    }
}
