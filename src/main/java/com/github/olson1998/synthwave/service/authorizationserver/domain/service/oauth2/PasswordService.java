package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PasswordModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPasswordDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.PasswordRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class PasswordService implements PasswordRepository {

    private final PasswordEncoder passwordEncoder;

    private final UserPasswordDataSourceRepository userPasswordDataSourceRepository;

    @Override
    public void save(@NonNull Password password) {
        var rawPassword = password.getValue();
        var encodedPassword = passwordEncoder.encode(rawPassword);
        var encodedPasswordObj = new PasswordModel(
                encodedPassword,
                password.getUserId(),
                password.getExpirePeriod()
        );
        userPasswordDataSourceRepository.save(encodedPasswordObj);
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
