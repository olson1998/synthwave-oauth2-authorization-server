package com.github.olson1998.synthwave.service.authorizationserver.domain.service.rest;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.repository.IdCollectionConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

@RequiredArgsConstructor
public class DefaultIdCollectionConverter <T> implements IdCollectionConverter<T> {

    private final Function<String, T> singleIdDeserializer;

    @Override
    public Collection<T> convert(String source) {
        return Arrays.stream(StringUtils.split(source, ','))
                .map(singleIdDeserializer)
                .toList();
    }
}
