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
        mappings.addDeserializer(AuthoritiesBinding.class, new AuthoritiesBindingsStdDeserializer());
        mappings.addSerializer(AuthoritiesBinding.class, new AuthoritiesBindingsStdSerializer());
        mappings.addDeserializer(Authority.class, new AuthorityStdDeserializer());
        mappings.addSerializer(Authority.class, new AuthorityStdSerializer());
        mappings.addDeserializer(Password.class, new PasswordStdDeserializer());
        mappings.addSerializer(Password.class, new PasswordStdSerializer());
        mappings.addDeserializer(RedirectUri.class, new RedirectUriStdDeserializer());
        mappings.addSerializer(RedirectUri.class, new RedirectUriStdSerializer());
        mappings.addDeserializer(UserAffiliation.class, new UserAffiliationStdDeserializer());
        mappings.addSerializer(UserAffiliation.class, new UserAffiliationStdSerializer());
        this.module = mappings;
    }
}
