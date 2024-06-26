package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role;

import com.github.olson1998.synthwave.support.jpa.audit.CreatedOn;

public interface RoleBinding extends CreatedOn {

    Long getUserId();

    Long getRoleId();

}
