package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientSettings;
import com.github.olson1998.synthwave.support.hibernate.javatype.TSIDJavaType;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "RCLTST")
public class RegisteredClientSettingsData implements Persistable<TSID>, RegisteredClientSettings {

    @Id
    @Column(name = "RCLTID")
    @JdbcType(BigIntJdbcType.class)
    @JavaType(TSIDJavaType.class)
    private TSID registeredClientId;

    @Column(name = "RCLRPK")
    private boolean requireProofKey;

    @Column(name = "RCLRAC")
    private boolean requireAuthorizationConsent;

    public RegisteredClientSettingsData(@NonNull RegisteredClientSettings registeredClientSettings) {
        this.registeredClientId = registeredClientSettings.getRegisteredClientId();
        this.requireProofKey = registeredClientSettings.isRequireProofKey();
        this.requireAuthorizationConsent = registeredClientSettings.isRequireAuthorizationConsent();
    }

    @Override
    public TSID getId() {
        return registeredClientId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
