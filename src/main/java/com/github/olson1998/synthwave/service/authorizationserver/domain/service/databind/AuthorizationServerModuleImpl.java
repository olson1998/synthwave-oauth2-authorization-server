package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.databind.AuthorizationServerModule;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.*;
import lombok.Getter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Getter
public class AuthorizationServerModuleImpl implements AuthorizationServerModule {

    private final Module module;

    public AuthorizationServerModuleImpl() {
        var mappings= new SimpleModule();
        var registeredClientStdSerializer = new RegisteredClientStdSerializer();
        var registeredClientStdDeserializer = new RegisteredClientStdDeserializer();
        //entities
        var redirectStdSerializer = new RedirectStdSerializer();
        var redirectStdDeserializer = new RedirectStdDeserializer();
        mappings.addSerializer(Password.class, new PasswordStdSerializer());
        mappings.addDeserializer(Password.class, new PasswordStdDeserializer());
        mappings.addSerializer(UserPassword.class, new UserPasswordStdSerializer());
        mappings.addDeserializer(UserPassword.class, new UserPasswordStdDeserializer());
        mappings.addSerializer(Redirect.class, redirectStdSerializer);
        mappings.addDeserializer(Redirect.class, redirectStdDeserializer);
        mappings.addSerializer(RedirectEntity.class, new RedirectEntityStdSerializer(redirectStdSerializer));
        mappings.addDeserializer(RedirectEntity.class, new RedirectEntityStdDeserializer(redirectStdDeserializer));
        mappings.addSerializer(Affiliation.class, new UserAffiliationStdSerializer());
        mappings.addDeserializer(Affiliation.class, new UserAffiliationStdDeserializer());
        mappings.addSerializer(UserAccountLock.class, new UserAccountLockStdSerializer());
        mappings.addDeserializer(UserAccountLock.class, new UserAccountLockStdDeserializer());
        mappings.addSerializer(UserEntity.class, new UserEntityStdSerializer());
        mappings.addDeserializer(UserEntity.class, new UserEntityStdDeserializer());
        mappings.addSerializer(UserProperties.class, new UserPropertiesStdSerializer());
        mappings.addDeserializer(UserProperties.class, new UserPropertiesStdDeserializer());
        mappings.addDeserializer(ClientSecret.class, new ClientSecretStdDeserializer());
        mappings.addSerializer(ClientSecret.class, new ClientSecretStdSerializer());
        mappings.addSerializer(RegisteredClientSecret.class, new RegisteredClientSecretStdSerializer());
        mappings.addDeserializer(RegisteredClientSecret.class, new RegisteredClientSecretStdDeserializer());
        //Registered client
        mappings.addSerializer(RegisteredClient.class, registeredClientStdSerializer);
        mappings.addDeserializer(RegisteredClient.class, registeredClientStdDeserializer);
        mappings.addSerializer(AuthorizationGrantType.class, new AuthorizationGrantTypeStdSerializer());
        mappings.addDeserializer(AuthorizationGrantType.class, new AuthorizationGrantTypeStdDeserializer());
        mappings.addSerializer(ClientAuthenticationMethod.class, new ClientAuthenticationMethodStdSerializer());
        mappings.addDeserializer(ClientAuthenticationMethod.class, new ClientAuthenticationMethodsStdDeserializer());
        mappings.addSerializer(OAuth2TokenFormat.class, new OAuth2TokenFormatStdSerializer());
        mappings.addDeserializer(OAuth2TokenFormat.class, new OAuth2TokenFormatStdDeserializer());
        mappings.addSerializer(TokenSettings.class, new TokenSettingsStdSerializer());
        mappings.addDeserializer(TokenSettings.class, new TokenSettingsStdDeserializer());
        mappings.addSerializer(ClientSettings.class, new ClientSettingsStdSerializer());
        mappings.addDeserializer(ClientSettings.class, new ClientSettingsStdDeserializer());
        mappings.addSerializer(JwsAlgorithm.class, new JwsAlgorithmStdSerializer());
        mappings.addDeserializer(JwsAlgorithm.class, new JwsAlgorithmStdDeserializer());
        this.module = mappings;
    }
}
