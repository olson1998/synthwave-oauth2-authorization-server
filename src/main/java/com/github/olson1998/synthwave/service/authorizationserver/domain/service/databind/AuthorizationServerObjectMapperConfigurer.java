package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.databind.Module;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.DefaultApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.support.springbootstarter.jackson.config.ObjectMapperConfigurer;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static java.util.Map.entry;

@Getter
public class AuthorizationServerObjectMapperConfigurer implements ObjectMapperConfigurer {

    private final Map<Class<?>, Class<?>> abstractTypeMappings = Map.ofEntries(
            entry(ApplicationUser.class, DefaultApplicationUser.class)
    );

    private final Collection<Module> mappingModules = Collections.emptyList();

    @Override
    public <T> ObjectMapperConfigurer appendBinding(Class<T> abstractType, Class<? extends T> mappedType) {
        return this;
    }

    @Override
    public <T> ObjectMapperConfigurer appendBindings(Map<Class<T>, Class<? extends T>> abstractTypeMappings) {
        return this;
    }

    @Override
    public ObjectMapperConfigurer appendModule(Module module) {
        return this;
    }

    @Override
    public ObjectMapperConfigurer appendModules(Iterable<Module> modules) {
        return this;
    }
}
