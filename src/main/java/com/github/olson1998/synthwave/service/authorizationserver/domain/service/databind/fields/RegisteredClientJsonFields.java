package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind.fields;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegisteredClientJsonFields {

    public static final String REGISTERED_CLIENT_ID_JSON_PROPERTY = "id";

    public static final String REGISTERED_CLIENT_CLIENT_ID_JSON_PROPERTY = "client_id";

    public static final String REGISTERED_CLIENT_NAME_JSON_PROPERTY = "username";

    public static final String REGISTERED_CLIENT_SECRET_JSON_PROPERTY = "client_secret";

    public static final String REGISTERED_CLIENT_ID_ISS_TMP_JSON_PROPERTY = "client_id_iss";

    public static final String REGISTERED_CLIENT_CLIENT_SETTINGS_JSON_PROPERTY = "client_settings";

    public static final String REDIRECT_URIS_JSON_PROPERTY = "redirect_uri";

    public static final String POST_LOGOUT_REDIRECT_URIS_JSON_PROPERTY = "post_logout_redirect_uri";

    public static final String AUTHORIZATION_GRANT_TYPES_JSON_PROPERTY = "authorization_grant_types";

    public static final String CLIENT_AUTHENTICATION_METHODS_JSON_PROPERTY = "client_authentication_methods";

    public static final String REGISTERED_CLIENT_TOKEN_SETTINGS_JSON_PROPERTY = "token_settings";

}
