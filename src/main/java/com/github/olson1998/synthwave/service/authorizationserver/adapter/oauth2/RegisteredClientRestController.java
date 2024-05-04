package com.github.olson1998.synthwave.service.authorizationserver.adapter.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2RegisteredClientRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/registered-client")
public class RegisteredClientRestController {

    private final OAuth2RegisteredClientRepository oAuth2RegisteredClientRepository;

    @GetMapping(path = "/search", produces = APPLICATION_JSON_VALUE)
    public Page<? extends RegisteredClientProperties> getRegisteredClientBases(@RequestParam(name = "RCID", required = false) Long registeredClientId,
                                                                               @RequestParam(name = "CID", required = false) String clientIdPattern,
                                                                               @RequestParam(name = "NAME", required = false) String namePattern,
                                                                               @RequestParam(name = "TMP", required = false) MutableDateTime timestamp,
                                                                               @RequestParam(name = "FEXP", required = false, defaultValue = "true") boolean isFilterExp,
                                                                               @RequestParam(name = "FNAC", required = false, defaultValue = "true") boolean isFilterNac,
                                                                               @RequestParam(name = "PGSZ", required = false, defaultValue = "10") int pageSize,
                                                                               @RequestParam(name = "PGNU", required = false, defaultValue = "0") int page) {
        return oAuth2RegisteredClientRepository.searchRegisteredClient(
                registeredClientId,
                clientIdPattern,
                namePattern,
                timestamp,
                isFilterExp,
                isFilterNac,
                pageSize,
                page
        );
    }

}
