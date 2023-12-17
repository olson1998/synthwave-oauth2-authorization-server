package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind.fields;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenSettingsJsonFields {

    public static final String SELF_CONTAINED = "SELF_CONTAINED";

    public static final String REFERENCE= "REFERENCE";

    public static final String TOKEN_SETTINGS_SIGNATURE_ALG = "signature_algorithm";

    public static final String TOKEN_SETTINGS_ACCESS_TOKEN_FORMAT = "access_token_format";

    public static final String REUSE_REFRESH_TOKEN = "reuse_refresh_token";

    public static final String ACCESS_TOKEN_TIME_TO_LIVE = "access_token_exp";

    public static final String REFRESH_TOKEN_TIME_TO_LIVE = "refresh_token_exp";

    public static final String DEVICE_CODE_TIME_TO_LIVE = "device_code_time_exp";

    public static final String AUTHORIZATION_CODE_TIME_TO_LIVE = "authorization_code_exp";
}
