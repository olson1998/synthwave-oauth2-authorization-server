package com.github.olson1998.synthwave.service.authorizationserver.domain.model.csrf;

import com.github.olson1998.synthwave.support.csrf.model.DecodedCsrf;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class CsrfSessionToken {

    private int usageCount;

    private final DecodedCsrf decodedCsrf;

}
