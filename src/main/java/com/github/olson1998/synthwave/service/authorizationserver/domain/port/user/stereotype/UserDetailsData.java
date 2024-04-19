package com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.Identifiable;
import org.joda.time.MutableDateTime;

import java.util.Collection;
import java.util.Set;

public interface UserDetailsData extends Identifiable<Long> {

    String getUsername();

    String getCompanyCode();

    String getDivision();

    String getPassword();

    Boolean getEnabled();

    MutableDateTime getExpireOn();

    Set<String> getAuthorities();

    void addAuthorities(Collection<String> authorities);
}
