package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role.embbedable.RoleBindingValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.RoleBinding;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "ROLEBIND")
public class RoleBindingData implements Persistable<RoleBindingValue>, RoleBinding {

    @EmbeddedId
    private RoleBindingValue value;

    @Column(name = "RBCTMP")
    private MutableDateTime createdOn;

    public RoleBindingData(@NonNull RoleBinding roleBinding) {
        this.value = new RoleBindingValue(
                roleBinding.getUserId(),
                roleBinding.getRoleId()
        );
        this.createdOn = roleBinding.getCreatedOn();
    }

    @Override
    public RoleBindingValue getId() {
        return value;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public Long getUserId() {
        return Optional.ofNullable(value)
                .map(RoleBindingValue::getUserId)
                .orElse(null);
    }

    @Override
    public Long getRoleId() {
        return Optional.ofNullable(value)
                .map(RoleBindingValue::getRoleId)
                .orElse(null);
    }
}
