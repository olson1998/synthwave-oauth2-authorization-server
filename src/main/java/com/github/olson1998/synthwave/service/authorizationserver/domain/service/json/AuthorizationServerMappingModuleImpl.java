package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.json.AuthorizationServerMappingModule;
import lombok.Getter;

@Getter
public class AuthorizationServerMappingModuleImpl implements AuthorizationServerMappingModule {

    private final Module module;

    public AuthorizationServerMappingModuleImpl() {
        var mappings= new SimpleModule();
        mappings.addDeserializer(Password.class, new PasswordStdDeserializer());
        mappings.addSerializer(Password.class, new PasswordStdSerializer());
        mappings.addDeserializer(RedirectURI.class, new RedirectURIStdDeserializer());
        mappings.addSerializer(RedirectURI.class, new RedirectURIStdSerializer());
        mappings.addDeserializer(UserAffiliation.class, new UserAffiliationStdDeserializer());
        mappings.addSerializer(UserAffiliation.class, new UserAffiliationStdSerializer());
        this.module = mappings;
    }
}
