package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.ClientPrivateRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.CompanyPrivateRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.DivisionPrivateRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURIEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURIBind;
import io.hypersistence.tsid.TSID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.MODULE)
class RedirectURIBoundMapper {

    Collection<RedirectURIBind> mapBounds(@NonNull TSID registeredClientId,
                                          @NonNull String companyCode,
                                          @NonNull String division,
                                          @NonNull Collection<RedirectURIEntity> postLogin,
                                          @NonNull Collection<RedirectURIEntity> postLogout){
        return Stream.concat(postLogin.stream(), postLogout.stream())
                .map(redirectURI -> mapBound(redirectURI, registeredClientId, companyCode, division))
                .collect(Collectors.toSet());
    }

    private RedirectURIBind mapBound(@NonNull RedirectURIEntity redirectURI,
                                     @NonNull TSID registeredClientId,
                                     @NonNull String companyCode,
                                     @NonNull String division){
        var uri = redirectURI.getUri();
        var uriId = redirectURI.getId();
        RedirectURIBind redirectURIBind;
        if(isCompanyPrivate(uri)){
            redirectURIBind = new CompanyPrivateRedirectURI(uriId, companyCode);
        } else if (isDivisionPrivate(uri)) {
            redirectURIBind = new DivisionPrivateRedirectURI(uriId, division);
        }else {
            redirectURIBind = new ClientPrivateRedirectURI(uriId, registeredClientId);
        }
        return redirectURIBind;
    }

    private boolean isCompanyPrivate(@NonNull String uri){
        return StringUtils.containsAny(uri, "{company}");
    }

    private boolean isDivisionPrivate(@NonNull String uri){
        return StringUtils.containsAny(uri, "{division}");
    }

}
