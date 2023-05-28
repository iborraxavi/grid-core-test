package com.ibx.grid_core_test.infrastructure.repository;

import com.ibx.grid_core_test.domain.model.ProductPrice;
import com.ibx.grid_core_test.infrastructure.mapper.ProductPriceRepositoryMapper;
import com.ibx.grid_core_test.infrastructure.model.ProductPriceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.core.ReactiveSelectOperation;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductPriceRepositoryImplTest {

    private static final Long PRODUCT_ID = 10008L;

    private static final Integer BRAND_ID = 1;

    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.now();

    @Mock
    private ProductPriceRepositoryMapper productPriceRepositoryMapper;

    @Mock
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @InjectMocks
    private ProductPriceRepositoryImpl productPriceRepository;

    @Test
    @DisplayName("Find product prices by brand and date when select clause error")
    void findProductPricesByBrandAndDate_whenSelectClauseError_shouldReturnExpectedError() {
        final ReactiveSelectOperation.ReactiveSelect<ProductPriceEntity> reactiveSelect = mock(ReactiveSelectOperation.ReactiveSelect.class);
        final ReactiveSelectOperation.TerminatingSelect<ProductPriceEntity> terminatingSelect = mock(ReactiveSelectOperation.TerminatingSelect.class);
        final DataAccessException dataAccessException = mock(DataAccessException.class);

        when(r2dbcEntityTemplate.select(ProductPriceEntity.class)).thenReturn(reactiveSelect);
        when(reactiveSelect.matching(any(Query.class))).thenReturn(terminatingSelect);
        when(terminatingSelect.all()).thenReturn(Flux.error(dataAccessException));

        final var result = productPriceRepository.findProductPricesByBrandAndDate(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);

        StepVerifier.create(result)
                .expectError(DataAccessException.class)
                .verify();

        verify(r2dbcEntityTemplate, only()).select(ProductPriceEntity.class);
        verifyNoInteractions(productPriceRepositoryMapper);
    }

    @Test
    @DisplayName("Find product prices by brand and date when not found results")
    void findProductPricesByBrandAndDate_whenNotFoundResults_shouldReturnEmptyResponse() {
        final ReactiveSelectOperation.ReactiveSelect<ProductPriceEntity> reactiveSelect = mock(ReactiveSelectOperation.ReactiveSelect.class);
        final ReactiveSelectOperation.TerminatingSelect<ProductPriceEntity> terminatingSelect = mock(ReactiveSelectOperation.TerminatingSelect.class);

        when(r2dbcEntityTemplate.select(ProductPriceEntity.class)).thenReturn(reactiveSelect);
        when(reactiveSelect.matching(any(Query.class))).thenReturn(terminatingSelect);
        when(terminatingSelect.all()).thenReturn(Flux.empty());

        final var result = productPriceRepository.findProductPricesByBrandAndDate(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        verify(r2dbcEntityTemplate, only()).select(ProductPriceEntity.class);
        verifyNoInteractions(productPriceRepositoryMapper);
    }

    @Test
    @DisplayName("Find product prices by brand and date when found results")
    void findProductPricesByBrandAndDate_whenFoundResults_shouldReturnEmptyResponse() {
        final ReactiveSelectOperation.ReactiveSelect<ProductPriceEntity> reactiveSelect = mock(ReactiveSelectOperation.ReactiveSelect.class);
        final ReactiveSelectOperation.TerminatingSelect<ProductPriceEntity> terminatingSelect = mock(ReactiveSelectOperation.TerminatingSelect.class);
        final ProductPriceEntity firstProductPriceEntity = mock(ProductPriceEntity.class);
        final ProductPriceEntity secondProductPriceEntity = mock(ProductPriceEntity.class);
        final ProductPrice firstProductPrice = mock(ProductPrice.class);
        final ProductPrice secondProductPrice = mock(ProductPrice.class);

        when(r2dbcEntityTemplate.select(ProductPriceEntity.class)).thenReturn(reactiveSelect);
        when(reactiveSelect.matching(any(Query.class))).thenReturn(terminatingSelect);
        when(terminatingSelect.all()).thenReturn(Flux.just(firstProductPriceEntity, secondProductPriceEntity));
        when(productPriceRepositoryMapper.mapProductPrice(firstProductPriceEntity)).thenReturn(firstProductPrice);
        when(productPriceRepositoryMapper.mapProductPrice(secondProductPriceEntity)).thenReturn(secondProductPrice);

        final var result = productPriceRepository.findProductPricesByBrandAndDate(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);

        StepVerifier.create(result)
                .expectNext(firstProductPrice, secondProductPrice)
                .verifyComplete();

        verify(r2dbcEntityTemplate, only()).select(ProductPriceEntity.class);
        verify(productPriceRepositoryMapper).mapProductPrice(firstProductPriceEntity);
        verify(productPriceRepositoryMapper).mapProductPrice(secondProductPriceEntity);
        verifyNoMoreInteractions(productPriceRepositoryMapper);
    }
}
