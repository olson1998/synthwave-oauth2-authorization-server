package com.github.olson1998.synthwave.service.authorizationserver.adapter.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.AuthorizationGrantTypeEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.AuthorizationGrantTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/authorization-grant-type")
public class AuthorizationGrantTypeRestController {

    private final AuthorizationGrantTypeRepository authorizationGrantTypeRepository;

    @GetMapping(path = "/list", produces = APPLICATION_JSON_VALUE)
    public Collection<? extends AuthorizationGrantTypeEntity> getAuthorizationGrantTypes() {
        return authorizationGrantTypeRepository.getAllTypes();
    }

}
