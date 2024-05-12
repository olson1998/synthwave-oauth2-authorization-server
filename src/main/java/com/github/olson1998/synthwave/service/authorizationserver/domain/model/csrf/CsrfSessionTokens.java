package com.github.olson1998.synthwave.service.authorizationserver.domain.model.csrf;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class CsrfSessionTokens extends ArrayList<CsrfSessionToken> {

    public Optional<CsrfSessionToken> findByTokenId(UUID csrfId) {
        return stream()
                .filter(csrfSessionToken -> csrfId.equals(csrfSessionToken.getDecodedCsrf().getTokenId()))
                .findFirst();
    }

}
