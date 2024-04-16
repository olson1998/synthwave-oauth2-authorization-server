package com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype;

public interface DeleteAuthorizationGrantTypeResponse extends DeleteResponse<Long> {

    DeletedRows getDeletedBounds();

}
