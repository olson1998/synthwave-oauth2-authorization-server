package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy;

import com.github.olson1998.synthwave.support.jpa.audit.CreatedOn;

public interface AuthorityBinding extends CreatedOn {

    Long getUserId();

    Long getAuthorityId();

}
