package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.Authority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "ATHDTA")
public class AuthorityData implements Authority, Persistable<String>{

    @Id
    @Column(name = "ATHVAL")
    private String value;

    @Override
    public String getId() {
        return value;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public AuthorityData(@NonNull Authority authority) {
        this.value = authority.getValue();
    }
}
