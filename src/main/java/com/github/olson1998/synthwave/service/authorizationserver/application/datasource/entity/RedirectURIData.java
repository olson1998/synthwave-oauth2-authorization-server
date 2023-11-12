package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIScope;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURIEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import com.github.olson1998.synthwave.support.hibernate.javatype.TSIDJavaType;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.springframework.data.domain.Persistable;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIScope.POST_LOGIN;
import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIScope.POST_LOGOUT;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "REDURI")
public class RedirectURIData implements RedirectURIEntity, Persistable<TSID> {

    @Id
    @Column(name = "URIID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID id;

    @Column(name = "URIVAL", nullable = false)
    private String uri;

    @Column(name = "URISCP", nullable = false)
    @Enumerated(EnumType.STRING)
    private RedirectURIScope scope;

    public RedirectURIData(@NonNull RedirectURI redirectURI) {
        this.uri = redirectURI.getUri();
        if(redirectURI.isPostLogin()){
            this.scope = POST_LOGIN;
        } else if (redirectURI.isPostLogout()) {
            this.scope = POST_LOGOUT;
        }
    }

    @Override
    public boolean isPostLogin() {
        return scope == POST_LOGIN;
    }

    @Override
    public boolean isPostLogout() {
        return scope == POST_LOGOUT;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
