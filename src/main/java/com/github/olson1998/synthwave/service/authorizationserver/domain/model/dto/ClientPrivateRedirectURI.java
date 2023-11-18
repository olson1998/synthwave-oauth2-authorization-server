package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ClientPrivateRedirectURI extends RedirectURIBindDTO{

    private final TSID registeredClientId;

    public ClientPrivateRedirectURI(TSID uriId, TSID registeredClientId) {
        super(uriId);
        this.registeredClientId = registeredClientId;
    }

    @Override
    public boolean isClientPrivate() {
        return true;
    }

    @Override
    public boolean isCompanyPrivate() {
        return false;
    }

    @Override
    public boolean isDivisionPrivate() {
        return false;
    }

    @Override
    public Affiliation getAffiliationBind() {
        return null;
    }

}
