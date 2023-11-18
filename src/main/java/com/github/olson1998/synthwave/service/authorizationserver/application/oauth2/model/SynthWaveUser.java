package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.DefaultUserDetails;
import io.hypersistence.tsid.TSID;
import lombok.Getter;
import org.joda.time.Period;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Getter
public class SynthWaveUser implements DefaultUserDetails {

    private final TSID userId;

    private final String companyCode;

    private final String division;

    private final String username;

    private final PasswordEntity passwordEntityData;

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
                         PasswordEntity passwordEntityData,
                         boolean accountNonLocked) {
        this.userId = userId;
        this.companyCode = companyCode;
        this.division = division;
        this.username = username;
        this.enabled = enabled;
        this.passwordEntityData = passwordEntityData;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired =
                isNonExpired(userId.getInstant(), accountExpirePeriod);
        this.credentialsNonExpired = passwordEntityData.getLatestVersion() ||
                isPasswordNonExpired(passwordEntityData);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return passwordEntityData.getValue();
    }

    @Override
    public String getUsername() {
        return username;
    }

    private boolean isPasswordNonExpired(PasswordEntity passwordEntity){
        var issueInstant = passwordEntity.getId().getInstant();
        return passwordEntity.getOptionalExpirePeriod()
                .map(period -> isNonExpired(issueInstant, period))
                .orElse(true);
    }

    private boolean isNonExpired(Instant issueInstant, Period expirePeriod){
        return Optional.ofNullable(expirePeriod)
                .map(period ->{
                    var jodaInstant =
                            org.joda.time.Instant.ofEpochMilli(issueInstant.toEpochMilli());
                    var expireInstant = jodaInstant.plus(period.toStandardDuration());
                    return expireInstant.isAfterNow();
                }).orElse(true);
    }

    @Override
    public String toString() {
        var passwordId = Optional.ofNullable(passwordEntityData)
                .map(PasswordEntity::getId)
                .map(String::valueOf)
                .orElse("?");
        return "SynthWaveUser(" +
                "userId=" + userId +
                ", companyCode='" + companyCode + '\'' +
                ", division='" + division + '\'' +
                ", username='" + username + '\'' +
                ", password='" + passwordId + '\'' +
                ", enabled=" + enabled +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ')';
    }
}
