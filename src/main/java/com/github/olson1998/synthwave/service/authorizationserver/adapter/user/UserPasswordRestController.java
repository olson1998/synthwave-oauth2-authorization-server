package com.github.olson1998.synthwave.service.authorizationserver.adapter.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository.UserPasswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/user/password")
public class UserPasswordRestController {

    private final UserPasswordRepository userPasswordRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(path = "/update", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public void updateUserPassword(@RequestBody UserPassword userPassword) {
        userPasswordRepository.updateUserPassword(userPassword);
    }

}
