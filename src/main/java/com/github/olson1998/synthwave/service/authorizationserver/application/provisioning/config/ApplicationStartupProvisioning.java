package com.github.olson1998.synthwave.service.authorizationserver.application.provisioning.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.provisioning.repository.Provisioner;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Flux;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApplicationStartupProvisioning {

    private final List<Provisioner> provisionerList;

    @EventListener(ApplicationStartedEvent.class)
    public void afterPropertiesSet() {
        Flux.fromIterable(provisionerList)
                .doOnNext(Provisioner::provision)
                .collectList()
                .block();
    }
}
