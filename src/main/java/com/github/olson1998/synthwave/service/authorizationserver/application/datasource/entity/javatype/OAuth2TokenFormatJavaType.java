package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.lang.reflect.Type;
import java.util.Optional;

public class OAuth2TokenFormatJavaType extends AbstractJavaType<OAuth2TokenFormat> {

    protected OAuth2TokenFormatJavaType() {
        super(OAuth2TokenFormat.class);
    }

    @Override
    public <X> X unwrap(OAuth2TokenFormat value, Class<X> type, WrapperOptions options) {
        return Optional.ofNullable(value)
                .filter(oAuth2TokenFormat -> {
                    if(!type.equals(String.class)){
                        throw unknownUnwrap(type);
                    }
                    return true;
                }).map(OAuth2TokenFormat::getValue)
                .map(type::cast)
                .orElse(null);
    }

    @Override
    public <X> OAuth2TokenFormat wrap(X value, WrapperOptions options) {
        return Optional.ofNullable(value)
                .map(x -> {
                    if(x instanceof String string){
                        return string;
                    }
                    throw unknownWrap(x.getClass());
                }).map(OAuth2TokenFormat::new)
                .orElse(null);
    }
}
