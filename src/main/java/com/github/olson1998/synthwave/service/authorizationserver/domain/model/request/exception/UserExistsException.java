package com.github.olson1998.synthwave.service.authorizationserver.domain.model.request.exception;

public class UserExistsException extends RuntimeException {

    public UserExistsException(String username) {
        super("User with username: '%s' exists".formatted(username));
    }
}
