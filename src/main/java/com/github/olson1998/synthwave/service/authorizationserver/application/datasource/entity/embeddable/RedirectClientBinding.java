package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectClientBound;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class RedirectClientBinding implements Serializable, RedirectClientBound {

    private static final long serialVersionUID = -7148784709224368550L;
    private TSID registeredClientId;

    private TSID redirectId;

    public RedirectClientBinding(@NonNull RedirectClientBound redirectClientBound) {
        this.registeredClientId = redirectClientBound.getRegisteredClientId();
        this.redirectId =redirectClientBound.getRedirectId();
    }
}
