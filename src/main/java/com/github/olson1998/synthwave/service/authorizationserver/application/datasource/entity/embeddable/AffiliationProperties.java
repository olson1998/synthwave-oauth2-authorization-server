package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class AffiliationProperties implements Serializable, Affiliation {

    private static final long serialVersionUID = 4785745455719475260L;

    @Column(name = "CODE", length = 10)
    private String companyCode;

    @Column(name = "DIVI", length = 3)
    private String division;

    public AffiliationProperties(@NonNull Affiliation affiliation) {
        this.companyCode = affiliation.getCompanyCode();
        this.division = affiliation.getDivision();
    }

}
