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
public class UserAffiliationValues implements Serializable {

    private static final long serialVersionUID = 4785745455719475260L;
    @Column(name = "CONO")
    private String companyCode;

    @Column(name = "DIVI")
    private String division;

}
