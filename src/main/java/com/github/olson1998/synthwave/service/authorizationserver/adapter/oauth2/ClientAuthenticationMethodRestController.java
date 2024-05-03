package com.github.olson1998.synthwave.service.authorizationserver.adapter.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientAuthenticationMethodEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.ClientAuthenticationMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/client-authentication-method")
public class ClientAuthenticationMethodRestController {

    private final ClientAuthenticationMethodRepository clientAuthenticationMethodRepository;

    @GetMapping(path = "/list")
    public Collection<? extends ClientAuthenticationMethodEntity> getAllClientAuthenticationMethods() {
        return clientAuthenticationMethodRepository.getAllMethods();
    }

}
