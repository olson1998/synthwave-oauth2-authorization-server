package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2AuthorizationRepository;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OAuth2AuthorizationService implements OAuth2AuthorizationRepository {

    private final Map<String, OAuth2Authorization> oAuth2AuthorizationMap = new HashMap<>();

    @Override
    public void save(OAuth2Authorization authorization) {
        oAuth2AuthorizationMap.put(authorization.getId(), authorization);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
         oAuth2AuthorizationMap.remove(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return oAuth2AuthorizationMap.get(id);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        return oAuth2AuthorizationMap.values()
                .stream()
                .filter(oAuth2Authorization -> {
                    var accessToken = oAuth2Authorization.getToken(token);
                    if(accessToken != null) {
                        return true;
                    } else {
                        return false;
                    }
                }).findAny()
                .orElse(null);
    }
}
