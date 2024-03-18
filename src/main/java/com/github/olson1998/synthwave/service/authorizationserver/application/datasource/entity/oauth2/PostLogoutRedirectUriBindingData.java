package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.emb.UriBindingValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.UriBinding;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "OAU2LUIB")
public class PostLogoutRedirectUriBindingData implements Persistable<UriBindingValue>, UriBinding {

    @EmbeddedId
    private UriBindingValue properties;

    @Column(name = "RUCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    public PostLogoutRedirectUriBindingData(UriBinding uriBinding) {
        this.properties = new UriBindingValue(
                uriBinding.getRegisteredClientId(),
                uriBinding.getUriId()
        );
        this.createdOn = uriBinding.getCreatedOn();
    }

    @Override
    public Long getRegisteredClientId() {
        return Optional.ofNullable(properties)
                .map(UriBindingValue::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public Long getUriId() {
        return Optional.ofNullable(properties)
                .map(UriBindingValue::getUriId)
                .orElse(null);
    }

    @Override
    public UriBindingValue getId() {
        return properties;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
