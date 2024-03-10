package com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.CreatedOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.ExpireOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.Identifiable;
import com.github.olson1998.synthwave.support.web.util.URIModel;

public interface RedirectUri extends Identifiable<Long>, CreatedOn, ExpireOn {

    URIModel getModel();
}
