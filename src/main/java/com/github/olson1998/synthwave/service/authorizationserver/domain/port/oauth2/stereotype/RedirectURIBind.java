package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import io.hypersistence.tsid.TSID;

public interface RedirectURIBind {

    TSID getUriId();

    boolean isClientPrivate();

    boolean isCompanyPrivate();

    boolean isDivisionPrivate();

    Affiliation getAffiliationBind();

    TSID getRegisteredClientId();

}
