package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectUriScope;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class RedirectUrisValue implements Serializable {

    private static final long serialVersionUID = -7539401279039610339L;

    @Column(name = "URISCP")
    @Enumerated(EnumType.STRING)
    private RedirectUriScope scope;

    @Column(name = "URIVAL")
    private String uri;
}
