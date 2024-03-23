package com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.MutableDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityModel implements Authority {

    @JsonProperty(value = "ID")
    private Long id;

    @JsonProperty(value = "NAME")
    private String name;

    @JsonProperty(value = "CTMP")
    private MutableDateTime createdOn;

    @JsonProperty(value = "ATMP")
    private MutableDateTime activeFrom;

    @JsonProperty(value = "ETMP")
    private MutableDateTime expireOn;

    public AuthorityModel(Authority authority) {
        this.id = authority.getId();
        this.name = authority.getName();
        this.createdOn = authority.getCreatedOn();
        this.activeFrom = authority.getActiveFrom();
        this.expireOn = authority.getExpireOn();
    }
}
