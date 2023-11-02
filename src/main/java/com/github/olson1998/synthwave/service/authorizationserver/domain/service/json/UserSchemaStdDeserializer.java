package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserPropertiesDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserSchemaDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAffiliationEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserAffiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserSchema;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;
import org.joda.time.Period;

import java.io.IOException;
import java.util.Optional;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordDTO.PASSWORD_EXPIRE_PERIOD_JSON_FILED;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordDTO.PASSWORD_VALUE_JSON_FILED;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserAffiliationDTO.USER_AFFILIATION_CODE_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserAffiliationDTO.USER_AFFILIATION_DIVI_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserPropertiesDTO.USER_EXP_PERIOD_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserPropertiesDTO.USER_NAME_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserSchemaDTO.*;

class UserSchemaStdDeserializer extends AbstractObjectStdDeserializer<UserSchema> {

    UserSchemaStdDeserializer() {
        super(UserSchema.class);
    }

    @Override
    protected UserSchema deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var userProps = readJsonProperty(USER_SCHEMA_USER_JSON_FIELD, objectNode, objectCodec, UserProperties.class, true);
        var passwordProps = readJsonProperty(USER_SCHEMA_PASSWORD_JSON_FIELD, objectNode, objectCodec, Password.class, true);
        var affiliationProps = readJsonProperty(USER_SCHEMA_AFFILIATION_JSON_FIELD, objectNode, objectCodec, UserAffiliation.class, true);
        return new UserSchemaDTO(userProps, passwordProps, affiliationProps);
    }

}
