package com.github.olson1998.synthwave.service.authorizationserver.domain.model.role;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.joda.time.MutableDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleModel implements Role {

    @JsonProperty(value = "ID")
    private Long id;

    @JsonProperty(value = "NAME")
    private String name;

    @JsonProperty(value = "CTMP")
    private MutableDateTime createdOn;

    @JsonProperty(value = "ETMP")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MutableDateTime expireOn;

    public RoleModel(@NonNull Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.createdOn = role.getCreatedOn();
        this.expireOn = role.getExpireOn();
    }
}
