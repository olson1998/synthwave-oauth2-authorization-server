package com.github.olson1998.synthwave.service.authorizationserver.adapter.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientExtendedProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/oauth2/registered-client")
public class RegisteredClientRestController {

    private final OAuth2RegisteredClientRepository oAuth2RegisteredClientRepository;

    @GetMapping(path = "/list", produces = APPLICATION_JSON_VALUE)
    public Collection<? extends RegisteredClientExtendedProperties> getRegisteredClientBases(@RequestParam(value = "RCID", required = false) Long id,
                                                                                             @RequestParam(value = "CID", required = false) String clientId,
                                                                                             @RequestParam(value = "NAME", required = false) String name,
                                                                                             @RequestParam(value = "AGT", required = false) Collection<AuthorizationGrantType> authorizationGrantTypes,
                                                                                             @RequestParam(value = "CAM", required = false) Collection<ClientAuthenticationMethod> clientAuthenticationMethods) {
        return Collections.emptyList();
    }

}
