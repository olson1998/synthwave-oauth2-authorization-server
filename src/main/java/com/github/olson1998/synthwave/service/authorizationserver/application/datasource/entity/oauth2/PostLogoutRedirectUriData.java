package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.URIModelJavaType;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.PostLogoutRedirectUri;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import com.github.olson1998.synthwave.support.web.util.URIModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.MutableDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "LUIDTA")
public class PostLogoutRedirectUriData implements PostLogoutRedirectUri {

    @Id
    @Column(name = "LUID")
    private Long id;

    @Column(name = "LUURI")
    @JavaType(URIModelJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private URIModel model;

    @Column(name = "LUCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

}
