package com.github.olson1998.synthwave.service.authorizationserver.adapter.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/oauth2")
public class RedirectUriRestController {

    private final RedirectRepository redirectRepository;
}
