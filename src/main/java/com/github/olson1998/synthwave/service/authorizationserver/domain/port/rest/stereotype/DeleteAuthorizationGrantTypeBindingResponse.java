package com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype;

public interface DeleteAuthorizationGrantTypeBindingResponse extends DeleteResponse<Long> {

    Long getRegisteredClientId();

    DeletedRows getDeletedRows();
}
