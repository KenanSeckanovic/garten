package com.acme.garten.controller;

import com.acme.garten.service.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/// Handler für allgemeine Exceptions
@ControllerAdvice
class CommonExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExceptionHandler.class);

    /// Konstruktor mit package private für Spring
    CommonExceptionHandler() {
    }

    /// ExceptionHandler, wenn ein Garten gesucht wird aber nicht existiert
    ///
    /// @param ex zugehörige NotFoundException
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    void onNotFound(final NotFoundException ex) {
        LOGGER.debug("onNotFound: {}", ex.getMessage());
    }
}
