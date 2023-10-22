package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.mapping;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import static org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat.REFERENCE;
import static org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat.SELF_CONTAINED;

public class OAuth2TokenFormatJavaType extends AbstractJavaType<OAuth2TokenFormat> {

    public OAuth2TokenFormatJavaType() {
        super(OAuth2TokenFormat.class);
    }

    @Override
    public <X> X unwrap(OAuth2TokenFormat oAuth2TokenFormat, Class<X> aClass, WrapperOptions wrapperOptions) {
        if(oAuth2TokenFormat == null){
            return null;
        } else if(aClass.equals(String.class)){
            return aClass.cast(oAuth2TokenFormat.getValue());
        }else {
            throw unknownUnwrap(aClass);
        }
    }

    @Override
    public <X> OAuth2TokenFormat wrap(X x, WrapperOptions wrapperOptions) {
        if(x == null){
            return null;
        } else if (x instanceof String str) {
            if(str.equals(SELF_CONTAINED.getValue())){
                return SELF_CONTAINED;
            } else if (str.equals(REFERENCE.getValue())) {
                return REFERENCE;
            }else {
                throw unknownWrap(String.class);
            }
        } else {
            throw unknownWrap(x.getClass());
        }
    }
}
