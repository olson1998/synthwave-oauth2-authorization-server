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
public class AuthorityBindingValue implements Serializable {

    private static final long serialVersionUID = -222428871917135347L;

    @Column(name = "USID")
    private Long userId;

    @Column(name = "AUID")
    private Long authorityId;

}
