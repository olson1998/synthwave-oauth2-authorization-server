package com.github.olson1998.synthwave.service.authorizationserver;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.query.Solution;
import org.junit.jupiter.api.Test;

public class SolutionTest {

    @Test
    void should() {
        var output = Solution.run("5h", 11, 25);
        System.out.println(output);
    }

}
