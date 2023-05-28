package com.ibx.grid_core_test.domain.use_cases;

import com.ibx.grid_core_test.domain.model.ProductPrice;
import com.ibx.grid_core_test.domain.model.exception.ProductPriceNotFoundException;
import com.ibx.grid_core_test.domain.repository.ProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class GetPriceByProductUseCase {

    @Autowired
    private ProductPriceRepository productPriceRepository;

    public Mono<ProductPrice> dispatch(final Long productId, final Integer brandId, final LocalDateTime applicationDate) {
        return productPriceRepository.findProductPricesByBrandAndDate(productId, brandId, applicationDate)
                .reduce(this::compareProductPriceEntities)
                .switchIfEmpty(Mono.error(
                        new ProductPriceNotFoundException(String.format("Product price not found, brandId: %d, productId: %d, applicationDate: %s",
                                brandId, productId, applicationDate))));
    }

    private ProductPrice compareProductPriceEntities(final ProductPrice firstProductPriceEntity,
                                                     final ProductPrice secondProductPriceEntity) {
        if (firstProductPriceEntity.getPriority().compareTo(secondProductPriceEntity.getPriority()) > 0) {
            return firstProductPriceEntity;
        }
        return secondProductPriceEntity;
    }
}
