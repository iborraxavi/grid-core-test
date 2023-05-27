package com.ibx.grid_core_test.domain.repository;

import com.ibx.grid_core_test.domain.model.ProductPriceEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface ProductPriceRepository extends R2dbcRepository<ProductPriceEntity, Long> {

    @Query("SELECT * FROM PRODUCT_PRICES p WHERE p.BRAND_ID = :brandId AND p.PRODUCT_ID = :productId AND p.START_DATE <= :applicationDate AND p.END_DATE >= :applicationDate")
    Flux<ProductPriceEntity> findProductPricesByBrandAndDate(final Long productId, final Integer brandId,
                                                             final LocalDateTime applicationDate);
}
