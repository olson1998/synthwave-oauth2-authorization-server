package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.AffiliationEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserMetadaModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserPasswordModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserPropertiesModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.AffiliationRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.PasswordRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.UserProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveUserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserMetadata;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultUserProvisioningService implements UserProvisioningRepository {

    private final PasswordRepository passwordRepository;

    private final AffiliationRepository affiliationRepository;

    private final UserDataSourceRepository userDataSourceRepository;

    @Override
    public boolean isProvisioningRequired(String username) {
        return userDataSourceRepository.existsUserWithGivenUsername(username);
    }

    @Override
    public UserMetadata provision(SynthWaveUserProperties synthWaveUserProperties){
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
        return new UserMetadaModel(userId, username, companyCode, division);
    }
}
