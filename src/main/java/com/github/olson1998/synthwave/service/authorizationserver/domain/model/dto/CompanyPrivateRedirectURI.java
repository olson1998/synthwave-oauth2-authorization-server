package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class CompanyPrivateRedirectURI extends RedirectURIBindDTO{

    private final Affiliation affiliationBind;

    public CompanyPrivateRedirectURI(TSID uriId, String companyCode) {
        super(uriId);
        this.affiliationBind = new AffiliationDTO(companyCode, null);
    }

    @Override
    public boolean isClientPrivate() {
        return false;
    }

    @Override
    public boolean isCompanyPrivate() {
        return true;
    }

    @Override
    public boolean isDivisionPrivate() {
        return false;
    }

    @Override
    public TSID getRegisteredClientId() {
        return null;
    }
}
