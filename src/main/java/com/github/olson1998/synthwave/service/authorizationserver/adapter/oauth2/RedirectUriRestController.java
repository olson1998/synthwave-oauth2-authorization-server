package com.github.olson1998.synthwave.service.authorizationserver.adapter.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/redirect-uri")
public class RedirectUriRestController {

    private final RedirectRepository redirectRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/save", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public Collection<? extends RedirectUri> saveRedirectUri(@RequestBody Collection<? extends RedirectUri> redirectUriCollection) {
        return redirectRepository.saveAllRedirectUri(redirectUriCollection);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/post-logout/save", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public Collection<? extends RedirectUri> savePostLogoutRedirectUri(@RequestBody Collection<? extends RedirectUri> redirectUriCollection) {
        return redirectRepository.saveAllPostLogoutRedirectUri(redirectUriCollection);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/bind/registered-client/{registeredClientId}/save", consumes = APPLICATION_JSON_VALUE)
    public void saveRegisteredClientRedirectUriBounds(@PathVariable("registeredClientId") Long registeredClientId, @RequestBody Collection<? extends RedirectUri> redirectUriCollection) {
        redirectRepository.saveAllRedirectBounds(redirectUriCollection, registeredClientId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/post-logout/bind/registered-client/{registeredClientId}/save", consumes = APPLICATION_JSON_VALUE)
    public void saveRegisteredClientPostLogoutRedirectUriBounds(@PathVariable("registeredClientId") Long registeredClientId, @RequestBody Collection<? extends RedirectUri> redirectUriCollection) {
        redirectRepository.saveAllPostLogoutRedirectBounds(redirectUriCollection, registeredClientId);
    }

}
