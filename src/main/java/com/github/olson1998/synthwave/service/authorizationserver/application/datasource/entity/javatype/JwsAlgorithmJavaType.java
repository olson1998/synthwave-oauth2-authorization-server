package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;

public class JwsAlgorithmJavaType extends AbstractJavaType<JwsAlgorithm> {

    protected JwsAlgorithmJavaType() {
        super(JwsAlgorithm.class);
    }

    @Override
    public <X> X unwrap(JwsAlgorithm value, Class<X> type, WrapperOptions options) {
        return null;
    }

    @Override
    public <X> JwsAlgorithm wrap(X value, WrapperOptions options) {
        return null;
    }
}
