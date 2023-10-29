package com.github.olson1998.synthwave.service.authorizationserver.domain.model.json;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class AuthoritiesBindingsJson implements AuthoritiesBinding {

    public static final String AUTHORITIES_BINDINGS_PARENT_AUTHORITY_JSON_FIELD = "parent";

    public static final String AUTHORITIES_BINDINGS_CHILD_AUTHORITY_JSON_FIELD = "child";

    private final String parentAuthority;

    private final String childAuthority;
}
