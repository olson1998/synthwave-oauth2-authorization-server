package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class DivisionPrivateRedirectURI extends RedirectURIBindDTO{

    private final Affiliation affiliationBind;

    public DivisionPrivateRedirectURI(TSID uriId, String division) {
        super(uriId);
        this.affiliationBind = new AffiliationDTO(null, division);
    }

    @Override
    public boolean isClientPrivate() {
        return false;
    }

    @Override
    public boolean isCompanyPrivate() {
        return false;
    }

    @Override
    public boolean isDivisionPrivate() {
        return true;
    }

    @Override
    public TSID getRegisteredClientId() {
        return null;
    }
}
