package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectClientBound;
import com.github.olson1998.synthwave.support.hibernate.javatype.TSIDJavaType;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class RedirectClientBinding implements Serializable, RedirectClientBound {

    private static final long serialVersionUID = -7148784709224368550L;

    @Column(name = "RCLID")
    @JdbcType(BigIntJdbcType.class)
    @JavaType(TSIDJavaType.class)
    private TSID registeredClientId;

    @Column(name = "URIID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID redirectId;

    public RedirectClientBinding(@NonNull RedirectClientBound redirectClientBound) {
        this.registeredClientId = redirectClientBound.getRegisteredClientId();
        this.redirectId =redirectClientBound.getRedirectId();
    }
}
