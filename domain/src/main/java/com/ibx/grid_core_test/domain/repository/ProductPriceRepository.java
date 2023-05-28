package com.ibx.grid_core_test.domain.repository;

import com.ibx.grid_core_test.domain.model.ProductPrice;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface ProductPriceRepository {

    Flux<ProductPrice> findProductPricesByBrandAndDate(final Long productId, final Integer brandId,
                                                       final LocalDateTime applicationDate);
}