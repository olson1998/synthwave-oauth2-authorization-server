package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.joda.time.Period;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Getter
@EqualsAndHashCode(callSuper = true)
public class SynthWaveUser extends SynthWaveUserMetadata implements UserDetails {

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
        super(userId, username, companyCode, division);
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
                "userId=" + super.getUserId() +
                ", companyCode='" + super.getCompanyCode() + '\'' +
                ", division='" + super.getDivision() + '\'' +
                ", username='" + super.getUsername() + '\'' +
                ", password='" + passwordId + '\'' +
                ", enabled=" + enabled +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ')';
    }
}
