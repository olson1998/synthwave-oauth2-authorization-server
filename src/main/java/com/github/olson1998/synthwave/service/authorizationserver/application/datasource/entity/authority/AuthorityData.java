package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority.AuthorityData.AUTHORITY_ID_SEQUENCE_GENERATOR;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = AUTHORITY_ID_SEQUENCE_GENERATOR, sequenceName = "AUIDSEQ", allocationSize = 1)

@Entity
@Table(name = "AUTHDATA")
public class AuthorityData implements Persistable<Long>, Authority {

    public static final String AUTHORITY_ID_SEQUENCE_GENERATOR = "AUIDSEQ";

    @Id
    @Column(name = "AUID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = AUTHORITY_ID_SEQUENCE_GENERATOR)
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

    public AuthorityData(Authority authority) {
        this.id = authority.getId();
        this.name = authority.getName();
        this.createdOn = authority.getCreatedOn();
        this.expireOn = authority.getExpireOn();
        this.activeFrom = authority.getActiveFrom();
    }
}
