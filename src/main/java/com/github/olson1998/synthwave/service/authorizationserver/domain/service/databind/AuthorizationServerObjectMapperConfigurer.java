package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.AuthorityBindingModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.AuthorityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.role.RoleBindingModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.role.RoleModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.ApplicationUserDetailsModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.ApplicationUserModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.UserPasswordModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.AuthorityBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.Role;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.RoleBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.ApplicationUserDetails;
import com.github.olson1998.synthwave.support.springbootstarter.service.ObjectMapperConfigurer;
import lombok.Getter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
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
            entry(ApplicationUserDetails.class, ApplicationUserDetailsModel.class),
            entry(UserPassword.class, UserPasswordModel.class),
            entry(Role.class, RoleModel.class),
            entry(Authority.class, AuthorityModel.class),
            entry(RoleBinding.class, RoleBindingModel.class),
            entry(AuthorityBinding.class, AuthorityBindingModel.class),
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
        module.addSerializer(JwsAlgorithm.class, new JwsAlgorithmStdSerializer());
        module.addDeserializer(JwsAlgorithm.class, new JwsAlgorithmStdDeserializer());
        module.addSerializer(RegisteredClient.class, new RegisteredClientStdSerializer());
        module.addDeserializer(RegisteredClient.class, new RegisteredClientStdDeserializer());
        return module;
    }
}
