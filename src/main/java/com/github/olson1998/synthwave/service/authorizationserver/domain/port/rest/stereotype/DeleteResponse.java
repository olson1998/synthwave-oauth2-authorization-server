package com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype;

public interface DeleteResponse<T> {

    T getParameters();
    
    DeletedRows getDeletedRows();

}
