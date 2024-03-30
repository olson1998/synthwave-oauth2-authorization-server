package com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;

import java.util.Collection;

public interface AuthorityDeleteResponse extends DeleteResponse<Collection<? extends Authority>> {

    int getDeleteBoundsCount();

}
