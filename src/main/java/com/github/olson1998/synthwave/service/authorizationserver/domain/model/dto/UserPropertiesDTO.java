package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.Period;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserPropertiesDTO implements UserProperties {

    private final String username;

    private final Period expirePeriod;
}
