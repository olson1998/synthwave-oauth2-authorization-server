package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.joda.time.MutableDateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Getter
@EqualsAndHashCode(callSuper = true)
public class UserPropertiesData extends UserAffiliationData implements UserDetails {

    private final PasswordEntity passwordEntityData;

    private final boolean enabled;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    public UserPropertiesData(TSID userId,
                              String companyCode,
                              String division,
                              String username,
                              boolean enabled,
                              MutableDateTime accountExpDate,
                              PasswordEntity passwordEntityData,
                              boolean accountNonLocked) {
        super(userId, username, companyCode, division);
        this.enabled = enabled;
        this.passwordEntityData = passwordEntityData;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = isBeforeNow(accountExpDate);
        this.credentialsNonExpired = isBeforeNow(passwordEntityData.getExpireDateTime());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return passwordEntityData.getValue();
    }

    private boolean isBeforeNow(MutableDateTime mutableDateTime){
        return Optional.ofNullable(mutableDateTime)
                .map(dateTime -> dateTime.isBefore(MutableDateTime.now()))
                .orElse(true);
    }

    @Override
    public String toString() {
        var passwordId = Optional.ofNullable(passwordEntityData)
                .map(PasswordEntity::getId)
                .map(String::valueOf)
                .orElse("?");
        return "UserPropertiesData(" +
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
