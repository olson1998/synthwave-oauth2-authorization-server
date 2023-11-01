package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@Configuration
public class OAuth2RepositoriesConfig {

    @Bean
    public OAuth2TokenMapper oAuth2TokenMapper(JwtDecoder jwtDecoder,
                                               ObjectMapper objectMapper){
        return new OAuth2TokenMapperImpl(jwtDecoder, objectMapper);
    }

    @Bean
    public RegisteredClientMapper registeredClientMapper(){
        return new RegisteredClientMapperImpl();
    }

    @Bean
    public OAuth2AuthorizationMapper oAuth2AuthorizationMapper(ObjectMapper objectMapper,
                                                               OAuth2TokenMapper oAuth2TokenMapper,
                                                               RegisteredClientMapper registeredClientMapper){
        return new OAuth2AuthorizationMapperImpl(objectMapper, oAuth2TokenMapper, registeredClientMapper);
    }

    @Bean
    public RegisteredClientRepository synthWaveRegisteredClientRepository(RegisteredClientMapper registeredClientMapper,
                                                                          UserPropertiesJpaRepositoryProxy userPropertiesSourceRepository,
                                                                          RedirectURIsJpaRepositoryProxy redirectURIsJpaRepositoryProxy,
                                                                          RegisteredClientJpaRepositoryProxy synthWaveRegisteredClientJpaRepositoryProxy){
        return new RegisteredClientServiceInstance(
                registeredClientMapper,
                redirectURIsJpaRepositoryProxy,
                userPropertiesSourceRepository,
                synthWaveRegisteredClientJpaRepositoryProxy
        );
    }

    @Bean
    public UserDetailsRepository synthWaveUserDetailsRepository(UserPropertiesJpaRepositoryProxy synthWaveUserDataSourceRepositoryProxy){
        return new UserDetailsService(synthWaveUserDataSourceRepositoryProxy);
    }

    @Bean
    public OAuth2AuthorizationRepository oAuth2AuthorizationRepository(OAuth2TokenMapper oAuth2TokenMapper,
                                                                       OAuth2AuthorizationMapper oAuth2AuthorizationMapper,
                                                                       OAuth2TokenJpaRepositoryProxy oAuth2TokenJpaRepositoryProxy,
                                                                       RedirectURIsJpaRepositoryProxy redirectURIsJpaRepositoryProxy,
                                                                       OAuth2AuthorizationJpaRepositoryProxy oAuth2AuthorizationJpaRepositoryProxy,
                                                                       RegisteredClientJpaRepositoryProxy registeredClientJpaRepositoryProxy){
        return new OAuth2AuthorizationServiceInstance(
                oAuth2TokenMapper,
                oAuth2AuthorizationMapper,
                oAuth2TokenJpaRepositoryProxy,
                redirectURIsJpaRepositoryProxy,
                oAuth2AuthorizationJpaRepositoryProxy,
                registeredClientJpaRepositoryProxy
        );
    }

    @Bean
    public UserSchemaRepository userSchemaRepository(PasswordEncoder passwordEncoder,
                                                     UserPropertiesJpaRepositoryProxy userPropertiesJpaRepositoryProxy,
                                                     UserPasswordJpaRepositoryProxy userPasswordJpaRepositoryProxy,
                                                     UserAffiliationJpaRepositoryProxy userAffiliationJpaRepositoryProxy){
        return new UserSchemaService(
                passwordEncoder,
                userPropertiesJpaRepositoryProxy,
                userPasswordJpaRepositoryProxy,
                userAffiliationJpaRepositoryProxy
        );
    }
}
