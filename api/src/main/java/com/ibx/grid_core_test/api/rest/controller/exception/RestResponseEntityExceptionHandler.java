package com.ibx.grid_core_test.api.rest.controller.exception;

import com.ibx.grid_core_test.api.rest.model.error.DefaultErrorResponse;
import com.ibx.grid_core_test.domain.model.exception.ProductPriceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

import static com.ibx.grid_core_test.api.rest.controller.exception.ErrorMessageKeys.PRODUCT_PRICE_NOT_FOUND;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ProductPriceNotFoundException.class)
    public ResponseEntity<DefaultErrorResponse> handleProductPriceNotFoundException(final ProductPriceNotFoundException ex) {
        logger.error(ex.getMessage());

        return new ResponseEntity<>(mapDefaultErrorResponse(PRODUCT_PRICE_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    private DefaultErrorResponse mapDefaultErrorResponse(final String messageKey) {
        return DefaultErrorResponse.builder()
                .message(messageSource.getMessage(messageKey, null, Locale.ENGLISH))
                .build();
    }
}
