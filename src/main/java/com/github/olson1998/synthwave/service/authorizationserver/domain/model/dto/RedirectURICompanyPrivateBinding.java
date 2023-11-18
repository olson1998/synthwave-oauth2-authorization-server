package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class RedirectURICompanyPrivateBinding extends AbstractRedirectURIBindingEntity {

    public RedirectURICompanyPrivateBinding(TSID registeredClientId, RedirectURI redirectURI, String companyCode) {
        super(registeredClientId, redirectURI, new AffiliationDTO(companyCode, null));
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
}
