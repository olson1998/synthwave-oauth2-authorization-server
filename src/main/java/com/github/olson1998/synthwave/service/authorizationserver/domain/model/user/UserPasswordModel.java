package com.github.olson1998.synthwave.service.authorizationserver.domain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.joda.time.MutableDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordModel implements UserPassword {

    @JsonProperty(value = "ID")
    private Long id;

    @JsonProperty(value = "USID")
    private Long userId;

    @JsonProperty(value = "VAL", required = true)
    private String value;

    @JsonIgnore
    private Boolean active;

    @JsonProperty(value = "CTMP")
    private MutableDateTime createdOn;

    @JsonProperty(value = "ETMP")
    private MutableDateTime expireOn;

    public UserPasswordModel(@NonNull UserPassword password) {
        this.id = password.getId();
        this.userId = password.getUserId();
        this.value = password.getValue();
        this.active = password.getActive();
        this.createdOn = password.getCreatedOn();
        this.expireOn = password.getExpireOn();
    }
}
