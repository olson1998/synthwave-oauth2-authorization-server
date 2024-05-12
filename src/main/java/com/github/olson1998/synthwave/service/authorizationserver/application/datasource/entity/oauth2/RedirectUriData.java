package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.URIModelJavaType;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import com.github.olson1998.synthwave.support.jpa.audit.CreatedOnEntityListener;
import com.github.olson1998.synthwave.support.web.util.URIModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Persistable;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RedirectUriData.REDIRECT_URI_ID_SEQUENCE_GENERATOR;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@EntityListeners({CreatedOnEntityListener.class})
@SequenceGenerator(name = REDIRECT_URI_ID_SEQUENCE_GENERATOR, sequenceName = "RUIDSEQ", allocationSize = 1)

@Entity
@Table(name = "OAU2RURI")
public class RedirectUriData implements Persistable<Long>, RedirectUri {

    public static final String REDIRECT_URI_ID_SEQUENCE_GENERATOR = "RIDSEQ";

    @Id
    @Column(name = "RUID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = REDIRECT_URI_ID_SEQUENCE_GENERATOR )
    private Long id;

    @Column(name = "RURI")
    private String value;

    @Column(name = "RUCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    @Column(name = "RUETMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime expireOn;

    public RedirectUriData(RedirectUri redirectUri) {
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
