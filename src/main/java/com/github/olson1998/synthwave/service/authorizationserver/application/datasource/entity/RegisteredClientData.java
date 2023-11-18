package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientEntity;
import com.github.olson1998.synthwave.support.hibernate.javatype.TSIDJavaType;
import io.hypersistence.tsid.TSID;
import io.hypersistence.utils.hibernate.id.Tsid;
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
@Table(name = "O2ERCL")
public class RegisteredClientData implements RegisteredClientEntity, Persistable<TSID> {

    @Id
    @Tsid
    @Column(name = "RCLID")
    @JdbcType(BigIntJdbcType.class)
    @JavaType(TSIDJavaType.class)
    private TSID id;

    @Column(name = "USRID", nullable = false)
    @JdbcType(BigIntJdbcType.class)
    @JavaType(TSIDJavaType.class)
    private TSID userId;

    public RegisteredClientData(@NonNull RegisteredClientEntity registeredClientEntity) {
        this.id = registeredClientEntity.getId();
        this.userId = registeredClientEntity.getUserId();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
