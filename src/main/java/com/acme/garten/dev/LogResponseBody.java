package com.acme.garten.dev;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/// [ResponseBodyAdvice] zur Protokollierung des Response-Bodys.
///
/// @author [Jürgen Zimmermann](mailto:Juergen.Zimmermann@h-ka.de)
@ControllerAdvice
@Profile("log-body")
@SuppressWarnings("NullableProblems")
public class LogResponseBody implements ResponseBodyAdvice<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogResponseBody.class);

    /// Konstruktor mit _package private_ für _Spring_.
    LogResponseBody() {
    }

    @Override
    public boolean supports(
        final MethodParameter returnType,
        final Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return true;
    }

    @Override
    @Nullable
    public Object beforeBodyWrite(
        @Nullable final Object body,
        final MethodParameter returnType,
        final MediaType selectedContentType,
        @SuppressWarnings("MethodParameterNamingConvention")
        final Class<? extends HttpMessageConverter<?>> selectedConverterType,
        final ServerHttpRequest request,
        final ServerHttpResponse response
    ) {
        LOGGER.trace("{}", body);
        return body;
    }
}
