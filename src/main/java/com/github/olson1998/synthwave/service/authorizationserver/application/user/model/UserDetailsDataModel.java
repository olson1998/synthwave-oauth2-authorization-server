package com.github.olson1998.synthwave.service.authorizationserver.application.user.model;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.UserDetailsData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.MutableDateTime;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDataModel implements UserDetailsData {

    private Long id;

    private String username;

    private String companyCode;

    private String division;

    private String password;

    private Boolean enabled;

    private MutableDateTime expireOn;

    private final Set<String> authorities = new HashSet<>();

    @Override
    public void addAuthorities(Collection<String> authorities) {
        this.authorities.addAll(authorities);
    }
}
