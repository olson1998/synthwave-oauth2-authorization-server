package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserIdentifiers;
import com.github.olson1998.synthwave.support.hibernate.javatype.PeriodJavaType;
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
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.Period;
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
    @Tsid
    @Column(name = "UID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID id;

    @Column(name = "CID", unique = true, nullable = false)
    private String clientId;

    @Column(name = "USRNM", unique = true, nullable = false)
    private String username;

    @Column(name = "EMADD", unique = true, nullable = false)
    private String emailAddress;

    @Column(name = "USRENB", nullable = false)
    private boolean enabled;

    @Column(name = "USREXP")
    @JavaType(PeriodJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private Period expirePeriod;

    public UserData(String clientId, String username, String emailAddress, boolean enabled, Period expirePeriod) {
        this.clientId = clientId;
        this.username = username;
        this.emailAddress = emailAddress;
        this.enabled = enabled;
        this.expirePeriod = expirePeriod;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
