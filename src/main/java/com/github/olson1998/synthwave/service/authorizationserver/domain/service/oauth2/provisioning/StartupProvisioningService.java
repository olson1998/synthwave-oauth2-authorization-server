package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.exception.ProvisioningException;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.properties.StartupProvisioningProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RedirectProvisioningSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientProvisioningSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.StartupProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

@Slf4j
@RequiredArgsConstructor
public class StartupProvisioningService implements StartupProvisioningRepository {

    private final StartupProvisioningProperties startupProvisioningProperties;

    private final RedirectProvisioningSupplier redirectProvisioningSupplier;

    private final RegistrationClientProvisioningSupplier registrationClientProvisioningSupplier;

    private final RedirectRepository redirectRepository;

    private final RegisteredClientRepository registeredClientRepository;

    @Override
    public void provision() {
        var supplySuccessRef = new AtomicBoolean(true);
        var redirects = supplyProvisionedRedirect(supplySuccessRef);
        provisionDataSync(supplySuccessRef.get(), redirects, this::provisionRedirect);
        var registrationClients =supplyProvisionedRegisteredClient(supplySuccessRef);
        provisionDataSync(supplySuccessRef.get(), registrationClients, this::provisionRegistrationClient);
    }

    @Override
    public CompletableFuture<Void> provisionAsync() {
        return null;
    }

    private LinkedHashSet<Redirect> supplyProvisionedRedirect(AtomicBoolean supplySuccessRef){
        return redirectProvisioningSupplier.get()
                .orElse(null);
    }

    private LinkedHashSet<RegisteredClient> supplyProvisionedRegisteredClient(AtomicBoolean supplySuccessRef){
        return registrationClientProvisioningSupplier.get()
                .orElse(null);
    }

    private <T> void provisionDataSync(boolean provision,
                                       LinkedHashSet<T> provisionedData,
                                       BiConsumer<LinkedHashSet<T>, Boolean> provisionConsumer){
        Optional.ofNullable(provisionedData).ifPresent(provisionObjectSet ->{
            provisionConsumer.accept(provisionedData, provision);
        });
    }

    private void provisionRedirect(LinkedHashSet<Redirect> redirects, boolean provision){
        if(provision){
            redirectRepository.saveAll(redirects);
        }
    }

    private void provisionRegistrationClient(LinkedHashSet<RegisteredClient> registeredClients, boolean provision){
        if(provision){
            registeredClients.forEach(registeredClientRepository::saveRegisteredClient);
        }
    }

    private void fire(Throwable e, boolean isThrow){
        if(isThrow){
            throw new ProvisioningException("Failed to provision, reason:", e);
        }
    }

}
