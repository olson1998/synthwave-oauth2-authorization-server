package com.github.olson1998.synthwave.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.authorizationserver.domain.port.oauth2.datasource.stereotype.UserAffiliation;
import com.github.olson1998.synthwave.support.data.hibernate.TSIDJavaType;
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

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "USRAFF")
public class UserAffiliationData implements Persistable<TSID>, UserAffiliation {

    @Id
    @Column(name = "UID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID userId;

    @Column(name = "CONO")
    private String companyCode;

    @Column(name = "DIVI")
    private String division;

    @Override
    public TSID getId() {
        return userId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
