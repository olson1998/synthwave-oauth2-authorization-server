package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.GrantedAuthority;
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
@Table(name = "AUTHDATA")
public class GrantedAuthorityData implements Persistable<Long>, GrantedAuthority {

    @Id
    @Column(name = "AUID")
    private Long id;

    @Column(name = "AUNAME")
    private String name;

    @Column(name = "AUCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    @Column(name = "AUETMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime expireOn;

    @Column(name = "AUATMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime activeFrom;

    @Override
    public boolean isNew() {
        return true;
    }
}
