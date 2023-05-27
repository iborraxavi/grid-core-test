package com.ibx.grid_core_test.api.rest.controller;

import com.ibx.grid_core_test.api.rest.mapper.ProductPriceRestMapper;
import com.ibx.grid_core_test.api.rest.model.response.GetPriceByProductResponse;
import com.ibx.grid_core_test.domain.model.ProductPriceEntity;
import com.ibx.grid_core_test.domain.model.exception.ProductPriceNotFoundException;
import com.ibx.grid_core_test.domain.use_cases.GetPriceByProductUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductPriceControllerTest {

    private static final Long PRODUCT_ID = 1111111L;

    private static final Integer BRAND_ID = 1;

    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.now();

    @Mock
    private ProductPriceRestMapper productPriceRestMapper;

    @Mock
    private GetPriceByProductUseCase getPriceByProductUseCase;

    @InjectMocks
    private ProductPriceController productPriceController;

    @Test
    @DisplayName("Get price by product when get price by product use case error")
    void getPriceByProduct_whenGetPriceByProductUseCaseError_shouldReturnExpectedError() {
        when(getPriceByProductUseCase.dispatch(PRODUCT_ID, BRAND_ID, APPLICATION_DATE))
                .thenReturn(Mono.error(mock(ProductPriceNotFoundException.class)));

        final var result = productPriceController.getPriceByProduct(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);

        StepVerifier.create(result)
                .expectError(ProductPriceNotFoundException.class)
                .verify();

        verify(getPriceByProductUseCase, only()).dispatch(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);
        verifyNoInteractions(productPriceRestMapper);
    }

    @Test
    @DisplayName("Get price by product when success")
    void getPriceByProduct_whenSuccess_shouldReturnExpectedResponse() {
        final ProductPriceEntity productPriceEntity = mock(ProductPriceEntity.class);
        final GetPriceByProductResponse getPriceByProductResponse = mock(GetPriceByProductResponse.class);

        when(getPriceByProductUseCase.dispatch(PRODUCT_ID, BRAND_ID, APPLICATION_DATE))
                .thenReturn(Mono.just(productPriceEntity));
        when(productPriceRestMapper.mapGetPriceByProductResponse(productPriceEntity))
                .thenReturn(getPriceByProductResponse);

        final var result = productPriceController.getPriceByProduct(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);

        StepVerifier.create(result)
                .assertNext(getPriceByProductResponseResponseEntity -> {
                    assertNotNull(getPriceByProductResponseResponseEntity);
                    assertEquals(HttpStatus.OK, getPriceByProductResponseResponseEntity.getStatusCode());
                    assertEquals(getPriceByProductResponse, getPriceByProductResponseResponseEntity.getBody());
                })
                .verifyComplete();

        verify(getPriceByProductUseCase, only()).dispatch(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);
        verify(productPriceRestMapper, only()).mapGetPriceByProductResponse(productPriceEntity);
    }
}