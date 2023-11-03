package com.github.olson1998.synthwave.service.authorizationserver.domain.model.request;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import io.hypersistence.tsid.TSID;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class UserActivationToken {

    private static final MessageDigest USER_DIGEST = getUserDigest();

    private final TSID userId;

    private final String userSecret;

    public UserActivationToken(UserEntity userEntity) {
        this.userId = userEntity.getId();
        this.userSecret = writeUserSecret(userEntity);
    }

    @Override
    public String toString() {
        return new StringBuilder(userId.toString())
                .append('/')
                .append(userSecret)
                .toString();
    }

    private String writeUserSecret(UserEntity userEntity){
        return new String(USER_DIGEST.digest(userEntity.toString().getBytes()));
    }

    public static UserActivationToken fromString(String token){
        var parts = StringUtils.split(token, "/");
        var userId = TSID.from(parts[0]);
        var userSecret = parts[1];
        return new UserActivationToken(userId, userSecret);
    }

    @SneakyThrows
    private static MessageDigest getUserDigest(){
        return MessageDigest.getInstance("SHA-256");
    }
}
