package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "OAU2SCPD")
public class ScopeData implements Persistable<Long>, Scope {

    @Id
    @Column(name = "SCID")
    private Long id;

    @Column(name = "SCNAME")
    private String name;

    @Column(name = "SCCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    public ScopeData(Scope scope) {
        this.id = scope.getId();
        this.name = scope.getName();
        this.createdOn = scope.getCreatedOn();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
