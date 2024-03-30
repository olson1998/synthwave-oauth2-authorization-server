package com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.AuthorityBinding;

import java.util.Collection;

public interface AuthorityBindingDeleteResponse extends DeleteResponse<Collection<? extends AuthorityBinding>> {
}
