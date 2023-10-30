package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RegisteredClientType;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientProperites;
import com.github.olson1998.synthwave.support.hibernate.javatype.TSIDJavaType;
import io.hypersistence.tsid.TSID;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
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
@Table(name = "RCLTDT")
public class RegisteredClientData implements RegisteredClientProperites, Persistable<TSID> {

    @Id
    @Tsid
    @Column(name = "RCLTID")
    @JdbcType(BigIntJdbcType.class)
    @JavaType(TSIDJavaType.class)
    private TSID id;

    @Column(name = "UID", nullable = false)
    @JdbcType(BigIntJdbcType.class)
    @JavaType(TSIDJavaType.class)
    private TSID userId;

    @Column(name = "RCLTNM", nullable = false)
    private String clientId;

    @Column(name = "RCTYPE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private RegisteredClientType type;

    public RegisteredClientData(@NonNull RegisteredClientProperites registeredClientProperites) {
        this.id = registeredClientProperites.getId();
        this.type = null;
        this.userId = registeredClientProperites.getUserId();
        this.clientId = registeredClientProperites.getClientId();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
