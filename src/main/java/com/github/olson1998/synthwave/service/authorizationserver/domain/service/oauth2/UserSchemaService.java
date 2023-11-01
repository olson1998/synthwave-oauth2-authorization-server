package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordEntityDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserAffiliationEntityDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserAffiliationDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPasswordDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPropertiesDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.UserSchemaRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserSchema;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class UserSchemaService implements UserSchemaRepository {

    private final PasswordEncoder passwordEncoder;

    private final UserPropertiesDataSourceRepository userPropertiesDataSourceRepository;

    private final UserPasswordDataSourceRepository userPasswordDataSourceRepository;

    private final UserAffiliationDataSourceRepository userAffiliationDataSourceRepository;

    @Override
    public boolean existsSchema(@NonNull UserSchema userSchema) {
        return userPropertiesDataSourceRepository.existsUserWithGivenUsername(userSchema.getProperties().getUsername());
    }

    @Override
    public TSID save(@NonNull UserSchema userSchema) {
        var userProps = userSchema.getProperties();
        var passwordProps = userSchema.getPassword();
        var affiliationProperties = userSchema.getAffiliation();
        var savedUserProps = userPropertiesDataSourceRepository.save(userProps);
        var userId= savedUserProps.getId();
        var encryptedPassword = passwordEncoder.encode(passwordProps.getValue());
        var passwordData = new PasswordEntityDTO(
                null,
                userId,
                encryptedPassword,
                passwordProps.getOptionalExpirePeriod().orElse(null),
                true
        );
        userPasswordDataSourceRepository.save(passwordData);
        var affiliation = new UserAffiliationEntityDTO(
                userId,
                affiliationProperties.getCompanyCode(),
                affiliationProperties.getDivision()
        );
        userAffiliationDataSourceRepository.save(affiliation);
        log.info(
                "Saved USER: '{}', CODE: '{}' DIVI: '{}'",
                userProps.getUsername(),
                affiliation.getCompanyCode(),
                affiliation.getDivision()
        );
        return userId;
    }

}
