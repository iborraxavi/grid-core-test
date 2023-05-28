package com.ibx.grid_core_test.infrastructure.mapper;

import com.ibx.grid_core_test.domain.model.ProductPrice;
import com.ibx.grid_core_test.infrastructure.model.ProductPriceEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceRepositoryMapper {

    public ProductPrice mapProductPrice(final ProductPriceEntity productPriceEntity) {
        if (productPriceEntity == null) {
            return null;
        }

        return ProductPrice.builder()
                .id(productPriceEntity.getId())
                .brandId(productPriceEntity.getBrandId())
                .startDate(productPriceEntity.getStartDate())
                .endDate(productPriceEntity.getEndDate())
                .priceList(productPriceEntity.getPriceList())
                .productId(productPriceEntity.getProductId())
                .priority(productPriceEntity.getPriority())
                .price(productPriceEntity.getPrice())
                .currency(productPriceEntity.getCurrency())
                .build();
    }
}