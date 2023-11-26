package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;

import java.util.Collection;
import java.util.Set;

public interface RedirectDataSourceRepository {

    Collection<RedirectEntity> getRedirectByAffiliation(String companyCode, String division);

    Collection<RedirectEntity> getRedirectByRedirectAndPostLogoutURISet(Set<String> redirects,
                                                                        Set<String> postLogoutRedirects);

    Collection<RedirectEntity> getRedirectByRedirectAndPostLogoutURISetAndAffiliation(Set<String> redirects,
                                                                                      Set<String> postLogoutRedirects,
                                                                                      String companyCode,
                                                                                      String division);

    Collection<RedirectEntity> saveAll(Collection<Redirect> redirectSet);

}
