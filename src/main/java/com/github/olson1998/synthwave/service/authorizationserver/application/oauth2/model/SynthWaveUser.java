package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.Password;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveUserDetails;
import io.hypersistence.tsid.TSID;
import lombok.Getter;
import org.joda.time.Period;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@Getter
public class SynthWaveUser implements SynthWaveUserDetails {

    private final TSID userId;

    private final String companyCode;

    private final String division;

    private final String username;

    private final Password passwordData;

    private final boolean enabled;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    public SynthWaveUser(TSID userId,
                         String companyCode,
                         String division,
                         String username,
                         boolean enabled,
                         Period accountExpirePeriod,
                         Password passwordData,
                         boolean accountNonLocked) {
        this.userId = userId;
        this.companyCode = companyCode;
        this.division = division;
        this.username = username;
        this.enabled = enabled;
        this.passwordData = passwordData;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired =
                isExpired(userId.getInstant(), accountExpirePeriod);
        this.credentialsNonExpired = passwordData.getLatestVersion() ||
                 isPasswordExpired(passwordData);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return passwordData.getValue();
    }

    @Override
    public String getUsername() {
        return username;
    }

    private boolean isPasswordExpired(Password password){
        var issueInstant = password.getId().getInstant();
        return password.getOptionalExpirePeriod()
                .map(period -> isExpired(issueInstant, period))
                .orElse(false);
    }

    private boolean isExpired(Instant issueInstant, Period expirePeriod){
        var jodaInstant =
                org.joda.time.Instant.ofEpochMilli(issueInstant.toEpochMilli());
        var expireInstant = jodaInstant.plus(expirePeriod.toStandardDuration());
        return expireInstant.isAfterNow();
    }

}
