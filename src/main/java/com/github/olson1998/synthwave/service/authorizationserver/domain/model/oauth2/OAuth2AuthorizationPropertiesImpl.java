package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthorizationProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class OAuth2AuthorizationPropertiesImpl implements AuthorizationProperties {

    private final String id;

    private final String registeredClientId;

    private final String principal;

    private final AuthorizationGrantType authorizationGrantType;

    private final String attributesJSON;
}
