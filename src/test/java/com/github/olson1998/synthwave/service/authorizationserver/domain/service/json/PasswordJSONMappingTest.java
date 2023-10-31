package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.PasswordDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.Password;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIOException;

class PasswordJSONMappingTest extends AuthorizationServerObjectMappingTest<Password> {

    PasswordJSONMappingTest() {
        super(Password.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"uid","value"})
    void shouldThrowIOExceptionIfEntityMissGivenField(String field){
        assertThatIOException().isThrownBy(()->{
           if(field.equals("uid")){
               objectMapper.readValue("{\"value\":\"abc\"}", Password.class);
           }else if (field.equals("value")){
               objectMapper.readValue("{\"uid\":%s}".formatted(TSID.fast().toLong()), Password.class);
           }
        });
    }

    @Override
    protected Password testSerializableObject() {
        return new PasswordDTO(
                TSID.fast(),
                TSID.fast(),
                "pass",
                null,
                null
        );
    }

    @Override
    protected String testDeserializableJSON() {
        return "{\"value\":\"abc\",\"uid\":%s}"
                .formatted(TSID.fast().toLong());
    }
}
