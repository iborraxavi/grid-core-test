package com.ibx.grid_core_test.api.rest.controller.exception;

import com.ibx.grid_core_test.api.dto.DefaultErrorResponse;
import com.ibx.grid_core_test.domain.model.exception.ProductPriceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MissingRequestValueException;

import java.util.Locale;

import static com.ibx.grid_core_test.api.rest.controller.exception.ErrorMessageKeys.PRODUCT_PRICE_NOT_FOUND;
import static com.ibx.grid_core_test.api.rest.controller.exception.ErrorMessageKeys.UNEXPECTED_ERROR;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MissingRequestValueException.class)
    public ResponseEntity<DefaultErrorResponse> handleMissingRequestValueException(final MissingRequestValueException ex) {
        logger.error(ex.getMessage());

        return new ResponseEntity<>(mapDefaultErrorResponseWithMessage(ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductPriceNotFoundException.class)
    public ResponseEntity<DefaultErrorResponse> handleProductPriceNotFoundException(final ProductPriceNotFoundException ex) {
        logger.error(ex.getMessage());

        return new ResponseEntity<>(mapDefaultErrorResponse(PRODUCT_PRICE_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DefaultErrorResponse> handleRuntimeException(final RuntimeException ex) {
        logger.error(ex.getMessage(), ex);

        return new ResponseEntity<>(mapDefaultErrorResponse(UNEXPECTED_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private DefaultErrorResponse mapDefaultErrorResponse(final String messageKey) {
        return DefaultErrorResponse.builder()
                .message(messageSource.getMessage(messageKey, null, Locale.ENGLISH))
                .build();
    }

    private DefaultErrorResponse mapDefaultErrorResponseWithMessage(final String message) {
        return DefaultErrorResponse.builder()
                .message(message)
                .build();
    }
}
