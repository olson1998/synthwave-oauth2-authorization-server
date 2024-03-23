package com.github.olson1998.synthwave.service.authorizationserver.domain.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.MutableDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserModel implements ApplicationUser {

    public static final String APPLICATION_USER_ID_FIELD = "ID";

    public static final String APPLICATION_USER_NAME_FIELD = "NAME";

    public static final String APPLICATION_USER_DISPLAY_NAME_FIELD = "DSNM";

    public static final String APPLICATION_USER_CREATED_ON_FIELD = "CTMP";

    public static final String APPLICATION_USER_EXPIRE_ON_FIELD = "ETMP";

    @JsonProperty(value = APPLICATION_USER_ID_FIELD)
    private Long id;

    @JsonProperty(value = APPLICATION_USER_NAME_FIELD, required = true)
    private String username;

    @JsonProperty(value = APPLICATION_USER_DISPLAY_NAME_FIELD, required = true)
    private String displayName;

    @JsonProperty(value = APPLICATION_USER_CREATED_ON_FIELD)
    private MutableDateTime createdOn;

    @JsonProperty(value = APPLICATION_USER_EXPIRE_ON_FIELD)
    private MutableDateTime expireOn;

    public ApplicationUserModel(ApplicationUser applicationUser) {
        this.id = applicationUser.getId();
        this.username = applicationUser.getUsername();
        this.displayName = applicationUser.getDisplayName();
        this.createdOn = applicationUser.getCreatedOn();
        this.expireOn = applicationUser.getExpireOn();
    }
}
