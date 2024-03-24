package com.github.olson1998.synthwave.service.authorizationserver.adapter.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository.ApplicationUserRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.ApplicationUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.github.olson1998.synthwave.support.jackson.SynthWaveParsingModules.DEFAULT_DATE_TIME_FORMATTER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/user")
public class ApplicationUserRestController {

    private final ApplicationUserRepository applicationUserRepository;

    @GetMapping(path = "/get", produces = APPLICATION_JSON_VALUE)
    public ApplicationUserDetails getUserId(@RequestParam("id") Long userId, @RequestParam(value = "timestamp", required = false) String timestampValue) {
        var timestamp = DEFAULT_DATE_TIME_FORMATTER.parseMutableDateTime(timestampValue);
        return applicationUserRepository.getApplicationUserDetailsByIdAndTimestamp(userId, timestamp);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/save", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ApplicationUser saveUser(@RequestBody ApplicationUserDetails applicationUserDetails) {
        return applicationUserRepository.saveApplicationUserDetails(applicationUserDetails);
    }

}
