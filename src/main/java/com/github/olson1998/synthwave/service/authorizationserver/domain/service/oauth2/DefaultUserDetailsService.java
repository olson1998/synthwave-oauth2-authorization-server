package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.AffiliationEntityDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.AffiliationRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.PasswordRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.UserDetailsRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSavingRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsRepository {

    private final UserDataSourceRepository userDataSourceRepository;

    private final PasswordRepository passwordRepository;

    private final AffiliationRepository affiliationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDataSourceRepository.getUserDetailsByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User: '%s' has not been found".formatted(username)));
    }

    @Override
    public boolean existsUserDetailsForUsername(String username) {
        return userDataSourceRepository.existsUserWithGivenUsername(username);
    }

    @Override
    public void saveUser(@NonNull UserSavingRequest userSavingRequest) {
        var user = userDataSourceRepository.save(userSavingRequest.getUser());
        var affiliation = userSavingRequest.getAffiliation();
        var password = userSavingRequest.getPassword();
        var userId = user.getId();
        var affiliationObj = new AffiliationEntityDTO(
                userId,
                affiliation.getCompanyCode(),
                affiliation.getDivision()
        );
        var passwordObj = new PasswordDTO(
                password.getValue(),
                userId,
                password.getLatestVersion(),
                password.getOptionalExpirePeriod().orElse(null)
        );
        affiliationRepository.save(affiliationObj);
        passwordRepository.save(passwordObj);
    }
}
