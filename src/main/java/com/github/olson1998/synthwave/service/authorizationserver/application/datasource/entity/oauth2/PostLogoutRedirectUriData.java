package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.URIModelJavaType;
import com.github.olson1998.synthwave.support.web.util.URIModel;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

public class PostLogoutRedirectUriData {

    private Long id;

    @JavaType(URIModelJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private URIModel uriModel;
}
