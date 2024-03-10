package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype;

import com.github.olson1998.synthwave.support.web.util.URIModel;
import com.github.olson1998.synthwave.support.web.util.URIUtils;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;

import java.net.URI;
import java.util.Optional;

public class URIModelJavaType extends AbstractJavaType<URIModel> {

    protected URIModelJavaType() {
        super(URIModel.class);
    }

    @Override
    public <X> X unwrap(URIModel value, Class<X> type, WrapperOptions options) {
        return Optional.ofNullable(value)
                .filter(uriModel -> {
                    if(type.equals(String.class)){
                        return true;
                    }else {
                        throw unknownUnwrap(type);
                    }
                }).map(URIModel::toURI)
                .map(URI::toString)
                .map(type::cast)
                .orElse(null);
    }

    @Override
    public <X> URIModel wrap(X value, WrapperOptions options) {
        return Optional.ofNullable(value)
                .map(x -> {
                    if(x instanceof String string){
                        return string;
                    }else {
                        throw unknownWrap(x.getClass());
                    }
                }).map(URIUtils::read)
                .orElse(null);
    }
}
