package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.AffiliationProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AffiliationEntity;
import com.github.olson1998.synthwave.support.hibernate.javatype.TSIDJavaType;
import io.hypersistence.tsid.TSID;
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

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "AUEAFF")
public class AffiliationData implements AffiliationEntity, Persistable<TSID> {

    @Id
    @Column(name = "USRID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID userId;

    private AffiliationProperties properties;

    public AffiliationData(@NonNull AffiliationEntity affiliationEntity) {
        this.userId = affiliationEntity.getUserId();
        this.properties = new AffiliationProperties(
                affiliationEntity.getCompanyCode(),
                affiliationEntity.getDivision()
        );
    }

    @Override
    public TSID getId() {
        return userId;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public String getCompanyCode() {
        return Optional.ofNullable(properties)
                .map(AffiliationProperties::getCompanyCode)
                .orElse(null);
    }

    @Override
    public String getDivision() {
        return Optional.ofNullable(properties)
                .map(AffiliationProperties::getDivision)
                .orElse(null);
    }

}
