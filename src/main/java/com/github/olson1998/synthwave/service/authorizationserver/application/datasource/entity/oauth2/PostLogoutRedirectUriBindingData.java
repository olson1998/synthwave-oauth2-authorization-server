package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.emb.UriBindingValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.oauth2.PostLogoutRedirectUri;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.oauth2.UriBinding;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import com.github.olson1998.synthwave.support.web.util.URIModel;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.joda.time.MutableDateTime;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "LUBDTA")
public class PostLogoutRedirectUriBindingData implements UriBinding {

    @EmbeddedId
    private UriBindingValue binding;

    @Column(name = "RUCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;
    @Override
    public Long getRegisteredClientId() {
        return Optional.ofNullable(binding)
                .map(UriBindingValue::getRegisteredClientId)
                .orElse(null);
    }

    @Override
    public Long getUriId() {
        return Optional.ofNullable(binding)
                .map(UriBindingValue::getUriId)
                .orElse(null);
    }
}
