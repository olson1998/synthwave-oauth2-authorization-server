package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RedirectModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RedirectEntityModel extends RedirectModel implements RedirectEntity {

    public static final String REDIRECT_ID_JSON_FIELD = "id";

    private final TSID id;

    public RedirectEntityModel(TSID id, String uri, Scope scope) {
        super(uri, scope);
        this.id = id;
    }

    public RedirectEntityModel(TSID id, Redirect redirect) {
        super(redirect.getUri(), Scope.resolveScope(redirect));
        this.id = id;
    }
}
