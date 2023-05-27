package com.ibx.grid_core_test.api.rest.controller.exception;

import com.ibx.grid_core_test.api.rest.model.error.DefaultErrorResponse;
import com.ibx.grid_core_test.domain.model.exception.ProductPriceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import java.util.Locale;

import static com.ibx.grid_core_test.api.rest.controller.exception.ErrorMessageKeys.PRODUCT_PRICE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestResponseEntityExceptionHandlerTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private RestResponseEntityExceptionHandler restResponseEntityExceptionHandler;

    @Test
    @DisplayName("Handle product price not found exception when product price not found exception")
    void handleProductPriceNotFoundException_whenProductPriceNotFoundException_shouldReturnExpectedResponse() {
        final ProductPriceNotFoundException productPriceNotFoundException = mock(ProductPriceNotFoundException.class);
        final String errorMessage = "This is a error message";

        when(messageSource.getMessage(PRODUCT_PRICE_NOT_FOUND, null, Locale.ENGLISH)).thenReturn(errorMessage);

        final var result = restResponseEntityExceptionHandler
                .handleProductPriceNotFoundException(productPriceNotFoundException);

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        final DefaultErrorResponse defaultErrorResponse = result.getBody();
        assertNotNull(defaultErrorResponse);
        assertEquals(errorMessage, defaultErrorResponse.getMessage());
    }
}
