package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.databind.Module;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.json.AuthorizationServerMappingModule;
import com.github.olson1998.sythwave.support.jacksontest.ObjectMappingTest;

import java.util.Collection;
import java.util.List;

import static com.github.olson1998.sythwave.support.jackson.SynthWaveParsingModules.JODA_PARSING_MODULE;
import static com.github.olson1998.sythwave.support.jackson.SynthWaveParsingModules.TSID_PARSING_MODULE;

abstract class AuthorizationServerObjectMappingTest<T> extends ObjectMappingTest<T> {

    private static final AuthorizationServerMappingModule AUTHORIZATION_SERVER_MAPPING_MODULE =
            new AuthorizationServerMappingModuleImpl();

    protected AuthorizationServerObjectMappingTest(Class<T> clazz) {
        super(clazz);
    }

    @Override
    protected Collection<Module> mappingObjectModules() {
        return List.of(
                JODA_PARSING_MODULE,
                TSID_PARSING_MODULE,
                AUTHORIZATION_SERVER_MAPPING_MODULE.getModule()
        );
    }

    @Override
    protected T testSerializableObject() {
        return null;
    }

    @Override
    protected String testDeserializableJSON() {
        return null;
    }
}
