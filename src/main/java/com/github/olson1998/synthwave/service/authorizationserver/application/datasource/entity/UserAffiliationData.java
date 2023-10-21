package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.UserAffiliation;
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

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "USRAFF")
public class UserAffiliationData implements Persistable<TSID>, UserAffiliation {

    @Id
    @Tsid
    @Column(name = "UID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID userId;

    @Column(name = "CONO")
    private String companyCode;

    @Column(name = "DIVI")
    private String division;

    @Column(name = "ROLE")
    private String role;

    @Override
    public TSID getId() {
        return userId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
