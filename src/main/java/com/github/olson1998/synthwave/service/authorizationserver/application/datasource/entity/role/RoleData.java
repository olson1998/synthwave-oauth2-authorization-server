package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.Role;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import com.github.olson1998.synthwave.support.jpa.audit.CreatedOnEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

import static com.github.olson1998.synthwave.support.jpa.generator.GeneratorConfig.MUTABLE_DATETIME_TIMESTAMP_GENERATOR;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@EntityListeners({CreatedOnEntityListener.class})
@SequenceGenerator(name = RoleData.ROLE_ID_SEQUENCE_GENERATOR, sequenceName = "RLIDSEQ", allocationSize = 1)

@Entity
@Table(name = "ROLEDATA")
public class RoleData implements Persistable<Long>, Role {

    public static final String ROLE_ID_SEQUENCE_GENERATOR = "RLIDSEQ";

    @Id
    @Column(name = "RLID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = ROLE_ID_SEQUENCE_GENERATOR)
    private Long id;

    @Column(name = "RLNAME")
    private String name;

    @Column(name = "RLCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    @Column(name = "RLETMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime expireOn;

    public RoleData(@NonNull Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.createdOn = role.getCreatedOn();
        this.expireOn = role.getExpireOn();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
