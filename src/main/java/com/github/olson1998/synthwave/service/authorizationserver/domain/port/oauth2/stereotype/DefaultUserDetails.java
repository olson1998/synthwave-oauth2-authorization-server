package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import io.hypersistence.tsid.TSID;
import org.springframework.security.core.userdetails.UserDetails;

public interface DefaultUserDetails extends UserDetails {

    TSID getUserId();

    String getCompanyCode();

    String getDivision();

}
