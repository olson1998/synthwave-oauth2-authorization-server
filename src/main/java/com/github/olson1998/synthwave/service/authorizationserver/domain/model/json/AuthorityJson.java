package com.github.olson1998.synthwave.service.authorizationserver.domain.model.json;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class AuthorityJson implements Authority {

    private final String value;
}
