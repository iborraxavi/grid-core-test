package com.ibx.grid_core_test.infrastructure.repository;

import com.ibx.grid_core_test.domain.model.ProductPrice;
import com.ibx.grid_core_test.domain.repository.ProductPriceRepository;
import com.ibx.grid_core_test.infrastructure.mapper.ProductPriceRepositoryMapper;
import com.ibx.grid_core_test.infrastructure.model.ProductPriceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

import static com.ibx.grid_core_test.infrastructure.model.ProductPricesTableFields.*;
import static org.springframework.data.relational.core.query.Criteria.where;

@Repository
public class ProductPriceRepositoryImpl implements ProductPriceRepository {

    @Autowired
    private ProductPriceRepositoryMapper productPriceRepositoryMapper;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<ProductPrice> findProductPricesByBrandAndDate(final Long productId, final Integer brandId,
                                                              final LocalDateTime applicationDate) {
        return r2dbcEntityTemplate.select(ProductPriceEntity.class)
                .matching(buildFindProductPricesByBrandAndDateQuery(productId, brandId, applicationDate))
                .all()
                .map(productPriceRepositoryMapper::mapProductPrice);
    }

    private Query buildFindProductPricesByBrandAndDateQuery(final Long productId, final Integer brandId,
                                                            final LocalDateTime applicationDate) {
        return Query.query(where(PRODUCT_ID).is(productId)
                .and(BRAND_ID).is(brandId)
                .and(START_DATE).lessThanOrEquals(applicationDate)
                .and(END_DATE).greaterThanOrEquals(applicationDate));
    }
}