package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.mapping.AuthorizationGrantTypeJavaType;
import com.github.olson1998.synthwave.support.hibernate.javatype.TSIDJavaType;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.io.Serializable;

@Data
@NoArgsConstructor

@Embeddable
public class AuthorizationGrantTypeBind implements Serializable {

    private static final long serialVersionUID = -5454607683082707098L;

    @Column(name = "RCLTID")
    @JdbcType(BigIntJdbcType.class)
    @JavaType(TSIDJavaType.class)
    private TSID registeredClientId;

    @Column(name = "AUTHGT")
    @JavaType(AuthorizationGrantTypeJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private AuthorizationGrantType authorizationGrantType;
}
