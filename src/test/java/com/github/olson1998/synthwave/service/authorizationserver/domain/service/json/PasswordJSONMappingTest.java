package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordEntityDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIOException;

class PasswordJSONMappingTest extends AuthorizationServerObjectMappingTest<PasswordEntity> {

    PasswordJSONMappingTest() {
        super(PasswordEntity.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"uid","value"})
    void shouldThrowIOExceptionIfEntityMissGivenField(String field){
        assertThatIOException().isThrownBy(()->{
           if(field.equals("uid")){
               objectMapper.readValue("{\"value\":\"abc\"}", PasswordEntity.class);
           }else if (field.equals("value")){
               objectMapper.readValue("{\"uid\":%s}".formatted(TSID.fast().toLong()), PasswordEntity.class);
           }
        });
    }

    @Override
    protected PasswordEntity testSerializableObject() {
        return new PasswordEntityDTO(
                TSID.fast(),
                TSID.fast(),
                "pass",
                null,
                true
        );
    }

    @Override
    protected String testDeserializableJSON() {
        return "{\"value\":\"abc\",\"uid\":%s}"
                .formatted(TSID.fast().toLong());
    }
}
