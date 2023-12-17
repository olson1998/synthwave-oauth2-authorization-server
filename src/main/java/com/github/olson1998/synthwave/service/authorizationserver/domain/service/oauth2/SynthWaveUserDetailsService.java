package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.AffiliationEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserMetadataModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserPasswordModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserPropertiesModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.AffiliationRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.PasswordRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.UserDetailsRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveUserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class SynthWaveUserDetailsService implements UserDetailsRepository {

    private final PasswordRepository passwordRepository;

    private final AffiliationRepository affiliationRepository;

    protected final UserDataSourceRepository userDataSourceRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDataSourceRepository.getUserDetailsByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User: '%s' has not been found".formatted(username)));
    }

    @Override
    public Optional<UserMetadata> getUserMetadataByUsername(String username) {
        return userDataSourceRepository.getUserMetadataByUsername(username);
    }

    @Override
    public UserMetadata saveUser(SynthWaveUserProperties synthWaveUserProperties) {
        var username = synthWaveUserProperties.getUsername();
        var password = synthWaveUserProperties.getUserPassword();
        var companyCode = synthWaveUserProperties.getCompanyCode();
        var division = synthWaveUserProperties.getDivision();
        var userProps = new UserPropertiesModel(
                username,
                true,
                synthWaveUserProperties.getExpireDateTime()
        );
        var userEntity = userDataSourceRepository.save(userProps);
        var userId = userEntity.getId();
        var affiliation = new AffiliationEntityModel(
                userId,
                companyCode,
                division
        );
        affiliationRepository.save(affiliation);
        var passwordObj = new UserPasswordModel(
                userId,
                password.getValue(),
                password.getExpireDateTime()
        );
        passwordRepository.save(passwordObj);
        return new UserMetadataModel(userId, username, companyCode, division);
    }

}
