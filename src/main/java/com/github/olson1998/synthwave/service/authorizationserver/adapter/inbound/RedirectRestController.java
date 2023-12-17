package com.github.olson1998.synthwave.service.authorizationserver.adapter.inbound;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline.RedirectPipelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.github.olson1998.synthwave.service.authorizationserver.adapter.inbound.RedirectRestController.REDIRECT_REQUEST_MAPPING;
import static org.springframework.http.HttpStatus.ACCEPTED;

@Async
@RestController
@RequestMapping(value = REDIRECT_REQUEST_MAPPING)

@RequiredArgsConstructor
public class RedirectRestController {

    public static final String REDIRECT_REQUEST_MAPPING = "/component/redirect";

    public static final String REDIRECT_SAVE_PATH = "/save";

    private final RedirectPipelineRepository redirectPipelineRepository;

    @ResponseStatus(ACCEPTED)
    @PostMapping(path = REDIRECT_SAVE_PATH)
    public CompletableFuture<Collection<RedirectEntity>> saveRedirects(@RequestBody Set<Redirect> redirectSet){
        return redirectPipelineRepository.saveAll(redirectSet)
                .toFuture();
    }

}
