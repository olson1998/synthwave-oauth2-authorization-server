package com.github.olson1998.synthwave.service.authorizationserver.application.rest.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.service.rest.DefaultIdCollectionConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collection;

@Configuration
public class RequestParamConverterConfig implements WebMvcConfigurer {

    private final Converter<String, Collection<Long>> longIdConverter = new DefaultIdCollectionConverter<>(Long::parseLong);

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(longIdConverter);
    }
}
