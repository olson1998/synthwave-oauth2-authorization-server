package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.exception;

import com.github.olson1998.synthwave.support.rest.exception.InternalServerErrorWebException;

public class RegistrationClientProvisioningException extends RuntimeException {

    public RegistrationClientProvisioningException(Throwable cause) {
        super(cause);
    }

    public RegistrationClientProvisioningException(String message) {
        super(message);
    }
}
