package com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype;

import java.util.Collection;

public interface DeleteScopeBindingResponse extends DeleteResponse<Collection<Long>> {

    Long getRegisteredClientId();

}
