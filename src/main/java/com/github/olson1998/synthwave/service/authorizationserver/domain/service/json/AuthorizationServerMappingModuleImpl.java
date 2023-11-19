package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.json.AuthorizationServerMappingModule;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSavingRequest;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import lombok.Getter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Getter
public class AuthorizationServerMappingModuleImpl implements AuthorizationServerMappingModule {

    private final Module module;

    public AuthorizationServerMappingModuleImpl() {
        var mappings= new SimpleModule();
        mappings.addSerializer(Password.class, new PasswordStdSerializer());
        mappings.addDeserializer(Password.class, new PasswordStdDeserializer());
        mappings.addDeserializer(PasswordEntity.class, new PasswordEntityStdDeserializer());
        mappings.addSerializer(PasswordEntity.class, new PasswordEntityStdSerializer());
        mappings.addDeserializer(RedirectURI.class, new RedirectURIStdDeserializer());
        mappings.addSerializer(RedirectURI.class, new RedirectURIStdSerializer());
        mappings.addSerializer(Affiliation.class, new UserAffiliationStdSerializer());
        mappings.addDeserializer(Affiliation.class, new UserAffiliationStdDeserializer());
        mappings.addDeserializer(AffiliationEntity.class, new UserAffiliationEntityStdDeserializer());
        mappings.addSerializer(AffiliationEntity.class, new UserAffiliationEntityStdSerializer());
        mappings.addSerializer(OAuth2AccessToken.TokenType.class, new TokenTypeStdSerializer());
        mappings.addDeserializer(OAuth2AccessToken.TokenType.class, new TokenTypeStdDeserializer());
        mappings.addSerializer(UserAccountLock.class, new UserAccountLockStdSerializer());
        mappings.addDeserializer(UserAccountLock.class, new UserAccountLockStdDeserializer());
        mappings.addSerializer(UserAccountLockEntity.class, new UserAccountLockEntityStdSerializer());
        mappings.addDeserializer(UserAccountLockEntity.class, new UserAccountLockEntityStdDeserializer());
        mappings.addSerializer(UserEntity.class, new UserEntityStdSerializer());
        mappings.addDeserializer(UserEntity.class, new UserEntityStdDeserializer());
        mappings.addSerializer(UserProperties.class, new UserPropertiesStdSerializer());
        mappings.addDeserializer(UserProperties.class, new UserPropertiesStdDeserializer());
        mappings.addSerializer(UserSchema.class, new UserSchemaStdSerializer());
        mappings.addDeserializer(UserSchema.class, new UserSchemaStdDeserializer());
        mappings.addDeserializer(RegisteredClient.class, new RegisteredClientStdDeserializer());
        mappings.addDeserializer(AuthorizationGrantType.class, new AuthorizationGrantTypeStdDeserializer());
        mappings.addDeserializer(ClientAuthenticationMethod.class, new ClientAuthenticationMethodsStdDeserializer());
        mappings.addDeserializer(TokenSettings.class, new TokenSettingsStdDeserializer());
        mappings.addDeserializer(RegisteredClient.class, new RegisteredClientStdDeserializer());
        mappings.addDeserializer(RegistrationClientProvisioningRequest.class, new RegistrationClientProvisioningRequestStdDeserializer());
        mappings.addDeserializer(UserSavingRequest.class, new UserSavingRequestStdDeserializer());
        this.module = mappings;
    }
}
