package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class UserAffiliationProperties implements Serializable {

    private static final long serialVersionUID = 4785745455719475260L;
    @Column(name = "CODE", length = 10, nullable = false)
    private String companyCode;

    @Column(name = "DIVI", length = 3, nullable = false)
    private String division;

}
