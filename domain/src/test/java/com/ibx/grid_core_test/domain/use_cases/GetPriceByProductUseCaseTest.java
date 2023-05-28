package com.ibx.grid_core_test.domain.use_cases;

import com.ibx.grid_core_test.domain.model.ProductPrice;
import com.ibx.grid_core_test.domain.model.exception.ProductPriceNotFoundException;
import com.ibx.grid_core_test.domain.repository.ProductPriceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPriceByProductUseCaseTest {

    private static final Integer BRAND_ID = 1;

    private static final Long PRODUCT_ID = 12345678L;

    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.now();

    @Mock
    private ProductPriceRepository productPriceRepository;

    @InjectMocks
    private GetPriceByProductUseCase getPriceByProductUseCase;

    @Test
    @DisplayName("Dispatch when product price not found")
    void dispatch_whenProductPriceNotFound_shouldReturnExpectedError() {
        when(productPriceRepository.findProductPricesByBrandAndDate(PRODUCT_ID, BRAND_ID, APPLICATION_DATE))
                .thenReturn(Flux.empty());

        final var result = getPriceByProductUseCase.dispatch(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);

        StepVerifier.create(result)
                .expectError(ProductPriceNotFoundException.class)
                .verify();

        verify(productPriceRepository, only()).findProductPricesByBrandAndDate(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);
    }

    @Test
    @DisplayName("Dispatch when product price repository return one element")
    void dispatch_whenProductPriceRepositoryReturnOneElement_shouldReturnExpectedProductPriceEntity() {
        final ProductPrice productPriceEntity = mock(ProductPrice.class);

        when(productPriceRepository.findProductPricesByBrandAndDate(PRODUCT_ID, BRAND_ID, APPLICATION_DATE))
                .thenReturn(Flux.just(productPriceEntity));

        final var result = getPriceByProductUseCase.dispatch(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);

        StepVerifier.create(result)
                .expectNext(productPriceEntity)
                .verifyComplete();

        verify(productPriceRepository, only()).findProductPricesByBrandAndDate(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);
    }

    @Test
    @DisplayName("Dispatch when product price repository return multiple elements and first is prioritized")
    void dispatch_whenProductPriceRepositoryReturnMultipleElementsAndFirstIsPrioritized_shouldReturnFirstElement() {
        final ProductPrice firstproductPriceEntity = mock(ProductPrice.class);
        final ProductPrice secondProductPriceEntity = mock(ProductPrice.class);

        when(firstproductPriceEntity.getPriority()).thenReturn(2);
        when(secondProductPriceEntity.getPriority()).thenReturn(1);
        when(productPriceRepository.findProductPricesByBrandAndDate(PRODUCT_ID, BRAND_ID, APPLICATION_DATE))
                .thenReturn(Flux.just(firstproductPriceEntity, secondProductPriceEntity));

        final var result = getPriceByProductUseCase.dispatch(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);

        StepVerifier.create(result)
                .expectNext(firstproductPriceEntity)
                .verifyComplete();

        verify(productPriceRepository, only()).findProductPricesByBrandAndDate(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);
    }

    @Test
    @DisplayName("Dispatch when product price repository return multiple elements and second is prioritized")
    void dispatch_whenProductPriceRepositoryReturnMultipleElementsAndSecondIsPrioritized_shouldReturnSecondElement() {
        final ProductPrice firstproductPriceEntity = mock(ProductPrice.class);
        final ProductPrice secondProductPriceEntity = mock(ProductPrice.class);

        when(firstproductPriceEntity.getPriority()).thenReturn(1);
        when(secondProductPriceEntity.getPriority()).thenReturn(2);
        when(productPriceRepository.findProductPricesByBrandAndDate(PRODUCT_ID, BRAND_ID, APPLICATION_DATE))
                .thenReturn(Flux.just(firstproductPriceEntity, secondProductPriceEntity));

        final var result = getPriceByProductUseCase.dispatch(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);

        StepVerifier.create(result)
                .expectNext(secondProductPriceEntity)
                .verifyComplete();

        verify(productPriceRepository, only()).findProductPricesByBrandAndDate(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);
    }
}