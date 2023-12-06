package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.*;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class OAuth2RepositoriesConfig {

    @Bean
    public PasswordRepository passwordRepository(@NonNull PasswordEncoder passwordEncoder,
                                                 @NonNull UserPasswordDataSourceRepository userPasswordDataSourceRepository){
        return new PasswordService(passwordEncoder, userPasswordDataSourceRepository);
    }

    @Bean
    public AffiliationRepository affiliationRepository(AffiliationDataSourceRepository affiliationDataSourceRepository){
        return new AffiliationService(affiliationDataSourceRepository);
    }

    @Bean
    public UserDetailsRepository userDetailsRepository(@NonNull UserDataSourceRepository userDataSourceRepository,
                                                       @NonNull PasswordRepository passwordRepository,
                                                       @NonNull AffiliationRepository affiliationRepository){
        return new SynthWaveUserDetailsService(passwordRepository, affiliationRepository, userDataSourceRepository);
    }

    @Bean
    public RedirectRepository redirectRepository(@NonNull RedirectDataSourceRepository redirectDataSourceRepository,
                                                 @NonNull RedirectClientBoundDataSourceRepository redirectClientBoundDataSourceRepository){
        return new RedirectService(redirectDataSourceRepository, redirectClientBoundDataSourceRepository);
    }

    @Bean
    public AuthorizationGrantTypeRepository authorizationGrantTypeRepository(@NonNull AuthorizationGrantTypeBindDataSourceRepository authorizationGrantTypeBindDataSourceRepository){
        return new AuthorizationGrantTypeService(authorizationGrantTypeBindDataSourceRepository);
    }

    @Bean
    public ClientAuthenticationMethodRepository clientAuthenticationMethodRepository(@NonNull ClientAuthenticationMethodBindDataSourceRepository clientAuthenticationMethodBindDataSourceRepository){
        return new ClientAuthenticationMethodService(clientAuthenticationMethodBindDataSourceRepository);
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(@NonNull PasswordEncoder passwordEncoder,
                                                                 @NonNull UserDetailsRepository userDetailsRepository,
                                                                 @NonNull RedirectRepository redirectRepository,
                                                                 @NonNull ClientAuthenticationMethodRepository clientAuthenticationMethodRepository,
                                                                 @NonNull AuthorizationGrantTypeRepository authorizationGrantTypeRepository,
                                                                 @NonNull RegisteredClientDataSourceRepository registeredClientDataSourceRepository,
                                                                 @NonNull RegisteredClientSecretDataSourceRepository registeredClientSecretDataSourceRepository,
                                                                 @NonNull RegisteredClientSettingsDataSourceRepository registeredClientSettingsDataSourceRepository,
                                                                 @NonNull RegisteredClientTokenSettingsDataSourceRepository registeredClientTokenSettingsDataSourceRepository){
        return new RegisteredClientService(
                passwordEncoder,
                userDetailsRepository,
                redirectRepository,
                clientAuthenticationMethodRepository,
                authorizationGrantTypeRepository,
                registeredClientDataSourceRepository,
                registeredClientSecretDataSourceRepository,
                registeredClientSettingsDataSourceRepository,
                registeredClientTokenSettingsDataSourceRepository
        );
    }

    @Bean
    public SynthWaveRegisteredClientRepository synthWaveRegisteredClientRepository(@NonNull RegisteredClientRepository registeredClientRepository){
        return new SynthWaveRegisteredClientService(registeredClientRepository);
    }

    @Bean
    public OAuth2AuthorizationRepository oAuth2AuthorizationRepository(){
        return new DefaultOAuth2AuthorizationService();
    }

}
