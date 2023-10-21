package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

public interface AuthoritiesBinding {

    String getParentAuthority();

    String getChildAuthority();
}
