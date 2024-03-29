package com.github.olson1998.synthwave.service.authorizationserver.adapter.authority;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.repository.AuthorityRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/authority")
public class AuthorityRestController {

    private final AuthorityRepository authorityRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/save", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public Collection<? extends Authority> saveAuthorities(@RequestBody Collection<Authority> authorityCollection) {
        return authorityRepository.saveAll(authorityCollection);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/binding/user/{userId}/save", consumes = APPLICATION_JSON_VALUE)
    public void saveAuthoritiesBounds(@PathVariable("userId") Long userId, @RequestBody Collection<Authority> authorityCollection) {
        authorityRepository.saveUserAuthorities(userId, authorityCollection);
    }

}
