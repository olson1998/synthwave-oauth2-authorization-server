package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIBindType;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.AffiliationProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURIBinding;
import com.github.olson1998.synthwave.support.hibernate.javatype.TSIDJavaType;
import io.hypersistence.tsid.TSID;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.springframework.data.domain.Persistable;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "O2ERUB")
public class RedirectURIBindingData implements Persistable<TSID>, RedirectURIBinding {

    @Id
    @Tsid
    @Column(name = "RUBID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID id;

    @Column(name = "URIID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID redirectURIId;

    @Column(name = "RUBTYP")
    private RedirectURIBindType type;

    @Column(name = "RCLID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID registeredClientId;

    private AffiliationProperties affiliationProperties;

    public RedirectURIBindingData(@NonNull RedirectURIBinding redirectURIBinding) {
        this.redirectURIId = redirectURIBinding.getRedirectURIId();
        this.registeredClientId = redirectURIBinding.getRegisteredClientId();
        this.affiliationProperties = Optional.ofNullable(redirectURIBinding.getAffiliation())
                .map(AffiliationProperties::new)
                .orElse(null);
    }

    @Override
    public Affiliation getAffiliation() {
        return affiliationProperties;
    }

    @Override
    public boolean isClientPrivate() {
        return type == RedirectURIBindType.CLIENT_PRIVATE;
    }

    @Override
    public boolean isCompanyPrivate() {
        return type == RedirectURIBindType.COMPANY_PRIVATE;
    }

    @Override
    public boolean isDivisionPrivate() {
        return type == RedirectURIBindType.DIVISION_PRIVATE;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
