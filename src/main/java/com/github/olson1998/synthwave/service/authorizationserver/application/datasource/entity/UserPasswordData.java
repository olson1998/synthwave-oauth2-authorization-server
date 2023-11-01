package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import com.github.olson1998.synthwave.support.hibernate.javatype.PeriodJavaType;
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
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.Period;
import org.springframework.data.domain.Persistable;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "PSSDTA")
public class UserPasswordData implements PasswordEntity, Persistable<TSID> {

    @Id
    @Tsid
    @Column(name = "PSSID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID id;

    @Column(name = "UID", nullable = false)
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID userId;

    @Column(name = "PSSVAL", nullable = false)
    private String value;

    @Column(name = "PSEXPD")
    @JavaType(PeriodJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private Period expirePeriod;

    @Column(name = "PSSVER", nullable = false)
    private Boolean latestVersion;

    public UserPasswordData(@NonNull PasswordEntity passwordEntity) {
        this.id = passwordEntity.getId();
        this.userId = passwordEntity.getUserId();
        this.value = passwordEntity.getValue();
        this.expirePeriod = passwordEntity.getOptionalExpirePeriod().orElse(null);
        this.latestVersion = passwordEntity.getLatestVersion();
    }

    public UserPasswordData(TSID userId, String value, Period expirePeriod, boolean latestVersion) {
        this.userId = userId;
        this.value = value;
        this.expirePeriod = expirePeriod;
        this.latestVersion = latestVersion;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public Optional<Period> getOptionalExpirePeriod() {
        return Optional.ofNullable(expirePeriod);
    }
}
