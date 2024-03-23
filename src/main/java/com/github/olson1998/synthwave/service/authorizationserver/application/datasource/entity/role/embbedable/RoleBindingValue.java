package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role.embbedable;

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
public class RoleBindingValue implements Serializable {

    private static final long serialVersionUID = -5046676066801467085L;

    @Column(name = "USID")
    private Long userId;

    @Column(name = "RLID")
    private Long roleId;

}
