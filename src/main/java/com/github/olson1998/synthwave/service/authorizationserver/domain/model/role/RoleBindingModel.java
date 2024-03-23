package com.github.olson1998.synthwave.service.authorizationserver.domain.model.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.RoleBinding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.MutableDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleBindingModel implements RoleBinding {

    @JsonProperty(value = "USID", required = true)
    private Long userId;

    @JsonProperty(value = "RLID", required = true)
    private Long roleId;

    @JsonProperty(value = "CTMP")
    private MutableDateTime createdOn;

}
