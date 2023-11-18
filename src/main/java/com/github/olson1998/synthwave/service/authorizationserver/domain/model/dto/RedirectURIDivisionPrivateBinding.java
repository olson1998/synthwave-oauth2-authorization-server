package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class RedirectURIDivisionPrivateBinding extends AbstractRedirectURIBindingEntity {

    public RedirectURIDivisionPrivateBinding(TSID registeredClientId, RedirectURI redirectURI, String division) {
        super(registeredClientId, redirectURI, new AffiliationDTO(null, division));
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
}
