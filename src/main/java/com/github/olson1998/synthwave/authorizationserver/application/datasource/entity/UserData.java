package com.github.olson1998.synthwave.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.authorizationserver.domain.port.oauth2.datasource.stereotype.UserIdentifiers;
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
@Table(name = "USRIDN")
public class UserData implements Persistable<TSID>, UserIdentifiers {

    @Id
    @Column(name = "UID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID id;

    @Column(name = "CID", unique = true, nullable = false)
    private String clientId;

    @Column(name = "EMADD", unique = true, nullable = false)
    private String emailAddress;

    @Override
    public boolean isNew() {
        return true;
    }
}
