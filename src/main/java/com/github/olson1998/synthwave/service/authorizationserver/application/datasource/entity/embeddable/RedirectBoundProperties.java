package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectBound;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
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
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class RedirectBoundProperties implements Serializable, RedirectBound {

    private static final long serialVersionUID = -2698328071315151610L;

    @Column(name = "URIID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID redirectId;

    private AffiliationProperties affiliationProperties;

    public RedirectBoundProperties(@NonNull RedirectBound redirectBound) {
        this.redirectId = redirectBound.getRedirectId();
        this.affiliationProperties = Optional.ofNullable(redirectBound.getAffiliation())
                .map(AffiliationProperties::new)
                .orElse(null);
    }

    @Override
    public Affiliation getAffiliation() {
        return affiliationProperties;
    }
}
