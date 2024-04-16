package com.github.olson1998.synthwave.service.authorizationserver.adapter.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.ScopeRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteScopeBindingResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteScopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/scope")
public class ScopeRestController {

    private final ScopeRepository scopeRepository;

    @GetMapping(path = "/list", produces = APPLICATION_JSON_VALUE)
    public Collection<? extends Scope> getAllScopes() {
        return scopeRepository.getAllScopes();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/save", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public Collection<? extends Scope> saveScopeCollection(@RequestBody Collection<? extends Scope> scopeCollection) {
        return scopeRepository.saveAllModels(scopeCollection);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/bind/registered-client/{registeredClientId}/save", consumes = APPLICATION_JSON_VALUE)
    public void saveScopeBounds(@PathVariable("registeredClientId") Long registeredClientId, @RequestBody Collection<? extends Scope> scopeCollection) {
        scopeRepository.saveAllBounds(scopeCollection, registeredClientId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/delete", produces = APPLICATION_JSON_VALUE)
    public DeleteScopeResponse deleteScope(@RequestParam("RCID") Collection<Long> idCollection) {
        return scopeRepository.deleteScopes(idCollection);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(path = "/bind/registered-client/{registeredClientId}/delete", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public DeleteScopeBindingResponse deleteScopeBounds(@PathVariable("registeredClientId") Long registeredClientId, @RequestBody Collection<? extends Scope> scopeCollection) {
        return scopeRepository.deleteScopeBounds(scopeCollection, registeredClientId);
    }

}
