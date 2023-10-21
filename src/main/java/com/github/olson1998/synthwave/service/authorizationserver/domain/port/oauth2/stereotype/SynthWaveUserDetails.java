package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import io.hypersistence.tsid.TSID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface SynthWaveUserDetails extends UserDetails {

    TSID getUserId();

    String getCompanyCode();

    String getDivision();

    String getRole();

    void grantAuthorities(Collection<GrantedAuthority> grantedAuthorities);
}
