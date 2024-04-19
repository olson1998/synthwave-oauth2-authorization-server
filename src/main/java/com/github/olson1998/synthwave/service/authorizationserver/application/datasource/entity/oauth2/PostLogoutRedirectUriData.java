package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.URIModelJavaType;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.PostLogoutRedirectUriData.POST_LOGOUT_REDIRECT_URI_ID_SEQUENCE_GENERATOR;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@SequenceGenerator(name = POST_LOGOUT_REDIRECT_URI_ID_SEQUENCE_GENERATOR, sequenceName = "LRIDSEQ", allocationSize = 1)

@Entity
@Table(name = "OAU2LURI")
public class PostLogoutRedirectUriData implements Persistable<Long>, RedirectUri {

    public static final String POST_LOGOUT_REDIRECT_URI_ID_SEQUENCE_GENERATOR = "LRIDSEQ";

    @Id
    @Column(name = "LRID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = POST_LOGOUT_REDIRECT_URI_ID_SEQUENCE_GENERATOR)
    private Long id;

    @Column(name = "LRURI")
    @JavaType(URIModelJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private String value;

    @Column(name = "LRCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    @Column(name = "LRETMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime expireOn;

    public PostLogoutRedirectUriData(RedirectUri redirectUri) {
        this.id = redirectUri.getId();
        this.value = redirectUri.getValue();
        this.createdOn = redirectUri.getCreatedOn();
        this.expireOn = redirectUri.getExpireOn();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
