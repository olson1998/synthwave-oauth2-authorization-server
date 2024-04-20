package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.emb;

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
public class PostLogoutUriBindingValue implements Serializable {

    private static final long serialVersionUID = -3414387887126129386L;
    @Column(name = "RCID")
    private Long registeredClientId;

    @Column(name = "LRID")
    private Long postLogoutUriId;

}
