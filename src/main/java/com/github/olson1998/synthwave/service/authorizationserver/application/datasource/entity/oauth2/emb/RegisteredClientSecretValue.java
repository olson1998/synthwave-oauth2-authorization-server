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
public class RegisteredClientSecretValue implements Serializable {

    private static final long serialVersionUID = 2763925792414413702L;

    @Column(name = "RCLID")
    private Long clientId;

    @Column(name = "RCSVAL")
    private String value;
}
