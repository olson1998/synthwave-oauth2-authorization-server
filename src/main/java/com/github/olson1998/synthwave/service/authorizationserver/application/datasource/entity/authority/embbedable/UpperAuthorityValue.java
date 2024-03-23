package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority.embbedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class UpperAuthorityValue implements Serializable {

    private static final long serialVersionUID = -950627239149362390L;

    @Column(name = "AUID")
    private Long authorityId;

    @Column(name = "PAID")
    private Long upperAuthorityId;
}
