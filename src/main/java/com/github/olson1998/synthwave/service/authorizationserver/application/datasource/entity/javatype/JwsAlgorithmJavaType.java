package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import java.util.Arrays;

public class JwsAlgorithmJavaType extends AbstractJavaType<JwsAlgorithm> {

    protected JwsAlgorithmJavaType() {
        super(JwsAlgorithm.class);
    }

    @Override
    public <X> X unwrap(JwsAlgorithm value, Class<X> type, WrapperOptions options) {
        if(value != null) {
            if(type.equals(String.class)) {
                return type.cast(value.getName());
            } else {
                throw unknownWrap(type);
            }
        } else {
            return null;
        }
    }

    @Override
    public <X> JwsAlgorithm wrap(X value, WrapperOptions options) {
        if(value != null) {
            if(value instanceof String textValue) {
                if(Arrays.stream(MacAlgorithm.values()).anyMatch(macAlgorithm -> macAlgorithm.getName().equals(textValue))) {
                    return MacAlgorithm.from(textValue);
                } else {
                    return SignatureAlgorithm.from(textValue);
                }
            } else {
                throw unknownWrap(value.getClass());
            }
        } else {
            return null;
        }
    }
}
