package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import io.hypersistence.tsid.TSID;

public interface RedirectURIBinding {

    TSID getRegisteredClientId();

    Affiliation getAffiliation();

    TSID getRedirectURIId();

    boolean isClientPrivate();

    boolean isCompanyPrivate();

    boolean isDivisionPrivate();
}
