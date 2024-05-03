package com.github.olson1998.synthwave.service.authorizationserver.domain.port.provisioning.repository;

public interface ProvisionerSource<T> {

    T getEntity();

}
