package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.emb.UriBindingValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.UriBinding;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import com.github.olson1998.synthwave.support.jpa.audit.CreatedOnEntityListener;
import jakarta.persistence.*;
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

@EntityListeners({CreatedOnEntityListener.class})

@Entity
@Table(name = "OAU2RUIB")
public class RedirectUriBindingData implements Persistable<UriBindingValue>, UriBinding {

    @EmbeddedId
    private UriBindingValue properties;

    @Column(name = "BNCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    public RedirectUriBindingData(UriBinding uriBinding) {
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
