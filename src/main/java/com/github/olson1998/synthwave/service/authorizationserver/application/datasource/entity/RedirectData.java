package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectScope;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import com.github.olson1998.synthwave.support.hibernate.javatype.TSIDJavaType;
import io.hypersistence.tsid.TSID;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
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
@Table(name = "O2EURI")
public class RedirectData implements Persistable<TSID>, RedirectEntity {

    @Id
    @Tsid
    @Column(name = "URIID")
    @JavaType(TSIDJavaType.class)
    @JdbcType(BigIntJdbcType.class)
    private TSID id;

    @Column(name = "URIVAL")
    private String uri;

    @Column(name = "URISCP")
    @Enumerated(EnumType.STRING)
    private RedirectScope scope;

    public RedirectData(Redirect redirect) {
        this.uri = redirect.getUri();
        if(redirect.isPostLogin()){
            this.scope = RedirectScope.POST_LOGIN;
        } else if (redirect.isPostLogout()) {
            this.scope = RedirectScope.POST_LOGOUT;
        }
    }

    @Override
    public boolean isPostLogin() {
        return scope == RedirectScope.POST_LOGIN;
    }

    @Override
    public boolean isPostLogout() {
        return scope == RedirectScope.POST_LOGOUT;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
