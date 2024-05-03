package com.github.olson1998.synthwave.service.authorizationserver.domain.service.provisioning;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.provisioning.repository.Provisioner;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.provisioning.repository.ProvisionerSource;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository.ApplicationUserRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.ApplicationUserDetails;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class UserProvisioningService implements Provisioner {

    private final ProvisionerSource<Collection<ApplicationUserDetails>> applicationUserDetailsProvisionerSource;

    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public void provision() {
        var applicationUserDetailsCollection = applicationUserDetailsProvisionerSource.getEntity();
        applicationUserDetailsCollection.forEach(applicationUserRepository::saveApplicationUserDetails);
    }
}
