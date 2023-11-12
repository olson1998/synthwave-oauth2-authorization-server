package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIBindType;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.AffiliationProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURIBindEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURIBind;
import com.github.olson1998.synthwave.support.hibernate.javatype.TSIDJavaType;
import io.hypersistence.tsid.TSID;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.springframework.data.domain.Persistable;

import java.util.Optional;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIBindType.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "RURIBN")
public class RedirectURIBindData implements Persistable<TSID>, RedirectURIBindEntity {
    
    @Id
    @Tsid
    @Column(name = "RUBNID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID id;
    
    @Column(name = "RUBNTP")
    private RedirectURIBindType type;

    @Column(name = "URIID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID uriId;
    
    @Column(name = "UID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID registeredClientId;
    
    private AffiliationProperties affiliation;

    public RedirectURIBindData(@NonNull RedirectURIBind redirectURIBind) {
        this.registeredClientId = redirectURIBind.getRegisteredClientId();
        this.uriId = redirectURIBind.getUriId();
        this.affiliation = Optional.ofNullable(redirectURIBind.getAffiliationBind())
                .map(AffiliationProperties::new)
                .orElse(null);
        if(redirectURIBind.isCompanyPrivate()){
            this.type = COMPANY_PRIVATE;
        } else if (redirectURIBind.isDivisionPrivate()) {
            this.type = DIVISION_PRIVATE;
        } else if (redirectURIBind.isClientPrivate()) {
            this.type = CLIENT_PRIVATE;
        }
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public boolean isClientPrivate() {
        return type == CLIENT_PRIVATE;
    }

    @Override
    public boolean isCompanyPrivate() {
        return type == COMPANY_PRIVATE;
    }

    @Override
    public boolean isDivisionPrivate() {
        return type == DIVISION_PRIVATE;
    }

    @Override
    public Affiliation getAffiliationBind() {
        return affiliation;
    }
}
