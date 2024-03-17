package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user.query;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.ApplicationUserDetailsSearchQueryResult;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class ApplicationUserDetailsSearchQueryResultImpl implements ApplicationUserDetailsSearchQueryResult {

    private String username;

    private String password;

    private Boolean enabled;

    private MutableDateTime expireOn;

    private final Set<String> authorities = new HashSet<>();

    @Override
    public void addAuthorities(Collection<String> authorities) {
        this.authorities.addAll(authorities);
    }
}
