package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@SequenceGenerator(name = RoleData.ROLE_ID_SEQUENCE_GENERATOR, sequenceName = "RLIDSEQ")

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
    private MutableDateTime createdOn;

    @Column(name = "RLETMP")
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
