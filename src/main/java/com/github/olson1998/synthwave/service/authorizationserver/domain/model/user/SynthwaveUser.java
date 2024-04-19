package com.github.olson1998.synthwave.service.authorizationserver.domain.model.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.AbstractSynthwaveUser;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SynthwaveUser extends AbstractSynthwaveUser {

    private final Long userId;

    private final String companyCode;

    private final String division;

    public SynthwaveUser(String username, String password, Long userId, String companyCode, String division) {
        super(username, password, Collections.emptyList());
        this.userId = userId;
        this.companyCode = companyCode;
        this.division = division;
    }

    @Builder(builderMethodName = "synthwaveUserBuilder", builderClassName = "SynthwaveUserBuilder")
    public SynthwaveUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Long userId, String companyCode, String division) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.companyCode = companyCode;
        this.division = division;
    }
}
