package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import com.github.olson1998.synthwave.support.jpa.audit.CreatedOnEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ScopeData.SCOPE_ID_SEQUENCE_GENERATOR;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@EntityListeners({CreatedOnEntityListener.class})
@SequenceGenerator(name = SCOPE_ID_SEQUENCE_GENERATOR, sequenceName = "SPIDSEQ", allocationSize = 1)

@Entity
@Table(name = "OAU2SCPD")
public class ScopeData implements Persistable<Long>, Scope {

    public static final String SCOPE_ID_SEQUENCE_GENERATOR = "SPIDSEQ";

    @Id
    @Column(name = "SPID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = SCOPE_ID_SEQUENCE_GENERATOR)
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
