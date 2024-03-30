package com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.AuthorityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.AuthorityDeleteResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDeleteResponseModel<T> implements AuthorityDeleteResponse {

    @JsonProperty("AUTHDATA")
    private Collection<AuthorityModel> entity;

    @JsonProperty("DELAUTHBIND")
    private int deleteBoundsCount;

}
