package com.github.olson1998.synthwave.service.authorizationserver.adapter.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/authorization-grant-type")
public class AuthorizationGrantTypeRestController {
}
