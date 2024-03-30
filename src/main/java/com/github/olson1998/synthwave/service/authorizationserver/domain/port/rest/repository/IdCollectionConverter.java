package com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.repository;

import org.springframework.core.convert.converter.Converter;

import java.util.Collection;

public interface IdCollectionConverter <T> extends Converter<String, Collection<T>> {
}
