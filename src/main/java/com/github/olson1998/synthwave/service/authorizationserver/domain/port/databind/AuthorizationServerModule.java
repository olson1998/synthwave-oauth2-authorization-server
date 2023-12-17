package com.github.olson1998.synthwave.service.authorizationserver.domain.port.databind;

import com.fasterxml.jackson.databind.Module;

public interface AuthorizationServerModule {

    Module getModule();
}
