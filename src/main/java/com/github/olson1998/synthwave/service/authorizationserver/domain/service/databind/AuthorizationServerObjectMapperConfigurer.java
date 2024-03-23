package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.ApplicationUserModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.support.springbootstarter.service.ObjectMapperConfigurer;
import lombok.Getter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static java.util.Map.entry;

@Getter
public class AuthorizationServerObjectMapperConfigurer implements ObjectMapperConfigurer {

    private final Map<Class<?>, Class<?>> abstractTypeMappings = Map.ofEntries(
            entry(ApplicationUser.class, ApplicationUserModel.class),
            entry(RegisteredClient.class, RegisteredClientModel.class),
            entry(RegisteredClientSecret.class, RegisteredClientSecretModel.class),
            entry(AuthorizationGrantTypeEntity.class, AuthorizationGrantTypeEntityModel.class),
            entry(ClientAuthenticationMethodEntity.class, ClientAuthenticationMethodEntityModel.class),
            entry(RedirectUri.class, RedirectUriModel.class),
            entry(Scope.class, ScopeModel.class),
            entry(TokenSettingsEntity.class, TokenSettingsEntityModel.class),
            entry(ClientSettingsEntity.class, ClientSettingsEntityModel.class)
    );

    private final Collection<Module> mappingModules = Collections.singletonList(createStdModule());

    @Override
    public <T> ObjectMapperConfigurer appendBinding(Class<T> abstractType, Class<? extends T> mappedType) {
        return null;
    }

    @Override
    public <T> ObjectMapperConfigurer appendBindings(Map<Class<T>, Class<? extends T>> abstractTypeMappings) {
        return null;
    }

    @Override
    public ObjectMapperConfigurer appendModule(Module module) {
        return null;
    }

    @Override
    public ObjectMapperConfigurer appendModules(Iterable<Module> modules) {
        return null;
    }

    private Module createStdModule() {
        var module = new SimpleModule();
        module.addSerializer(AuthorizationGrantType.class, new AuthorizationGrantTypeStdSerializer());
        module.addDeserializer(AuthorizationGrantType.class, new AuthorizationGrantTypeStdDeserializer());
        module.addSerializer(ClientAuthenticationMethod.class, new ClientAuthenticationMethodStdSerializer());
        module.addDeserializer(ClientAuthenticationMethod.class, new ClientAuthenticationMethodStdDeserializer());
        module.addSerializer(OAuth2TokenFormat.class, new OAuth2TokenFormatStdSerializer());
        module.addDeserializer(OAuth2TokenFormat.class, new OAuth2TokenFormatStdDeserializer());
        module.addSerializer(SignatureAlgorithm.class, new SignatureAlgorithmStdSerializer());
        module.addDeserializer(SignatureAlgorithm.class, new SignatureAlgorithmStdDeserializer());
        return module;
    }
}
