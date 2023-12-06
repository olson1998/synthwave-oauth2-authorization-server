package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.exception;

public class ProvisioningException extends RuntimeException {

    public ProvisioningException(Throwable cause) {
        super(cause);
    }

    public ProvisioningException(String message) {
        super(message);
    }

    public ProvisioningException(String message, Throwable cause) {
        super(message, cause);
    }

}
