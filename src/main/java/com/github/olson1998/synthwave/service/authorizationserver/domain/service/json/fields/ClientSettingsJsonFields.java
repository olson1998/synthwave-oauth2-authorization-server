package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json.fields;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClientSettingsJsonFields {

    public static final String CLIENT_SETTINGS_REQUIRE_PROOF_KEY = "require_proof_key";

    public static final String CLIENT_SETTINGS_REQUIRE_AUTHORIZATION_CONSENT = "require_authorization_consent";

    public static final String CLIENT_SETTINGS_JWK_URL = "jwk_url";

    public static final String CLIENT_SETTINGS_AUTHENTICATION_SIGNING_ALG = "auth_signing_alg";
}
