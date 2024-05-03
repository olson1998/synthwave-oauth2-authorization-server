package com.github.olson1998.synthwave.service.authorizationserver.application.user.model;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.UserDetailsData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDetailsDataModel implements UserDetailsData {

    private final Long id;

    private final String username;

    private final String companyCode;

    private final String division;

    private final String password;

    private final Boolean enabled;

    private final MutableDateTime expireOn;

    private final Set<String> authorities;

    public UserDetailsDataModel(Long id,
                                String username,
                                String companyCode,
                                String division,
                                String password,
                                MutableDateTime expireOn) {
        this.id = id;
        this.username = username;
        this.companyCode = companyCode;
        this.division = division;
        this.password = password;
        this.enabled = true;
        this.expireOn = expireOn;
        this.authorities = new HashSet<>();
    }

    @Override
    public void addAuthorities(Collection<String> authorities) {
        this.authorities.addAll(authorities);
    }
}
