package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.AuthorityDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPropertiesSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.SynthWaveUserDetailsRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class SynthWaveUserDetailsService implements SynthWaveUserDetailsRepository {

    private final UserPropertiesSourceRepository userDataSourceRepository;

    private final AuthorityDataSourceRepository authorityDataSourceRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDataSourceRepository.getSynthWaveUserByUsername(username)
                .map(this::appendGrantedAuthorities)
                .orElseThrow(()-> new UsernameNotFoundException("User: '%s' has not been found".formatted(username)));
    }

    private UserDetails appendGrantedAuthorities(SynthWaveUserDetails synthWaveUserDetails){
        var role = synthWaveUserDetails.getRole();
        var grantedAuthorities =
                authorityDataSourceRepository.getSimpleGrantedAuthoritiesAndAllChildAuthoritiesByParentValue(role);
        synthWaveUserDetails.grantAuthorities(grantedAuthorities);
        return synthWaveUserDetails;
    }
}
