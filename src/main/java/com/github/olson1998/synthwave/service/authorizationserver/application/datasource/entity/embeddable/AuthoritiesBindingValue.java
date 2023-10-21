package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class AuthoritiesBindingValue implements Serializable {

    private static final long serialVersionUID = 3925535214801488519L;
    @Column(name = "PRTATH", nullable = false)
    private String parentAuthority;

    @Column(name = "CLDATH", nullable = false)
    private String childAuthority;
}
