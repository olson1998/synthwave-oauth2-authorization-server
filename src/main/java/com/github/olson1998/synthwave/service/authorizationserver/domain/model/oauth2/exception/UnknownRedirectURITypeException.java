package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.exception;

public class UnknownRedirectURITypeException extends RuntimeException {

    public UnknownRedirectURITypeException(String type) {
        super("Unknown redirect URI type: '%s'".formatted(type));
    }
}
