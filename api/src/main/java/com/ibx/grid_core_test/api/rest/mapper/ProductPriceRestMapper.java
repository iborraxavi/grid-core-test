package com.ibx.grid_core_test.api.rest.mapper;

import com.ibx.grid_core_test.api.dto.GetPriceByProductResponse;
import com.ibx.grid_core_test.domain.model.ProductPrice;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceRestMapper {

    public GetPriceByProductResponse mapGetPriceByProductResponse(final ProductPrice productPriceEntity) {
        if (productPriceEntity == null) {
            return null;
        }

        return GetPriceByProductResponse.builder()
                .priceList(productPriceEntity.getPriceList())
                .productId(productPriceEntity.getProductId())
                .brandId(productPriceEntity.getBrandId())
                .startDate(productPriceEntity.getStartDate())
                .endDate(productPriceEntity.getEndDate())
                .price(productPriceEntity.getPrice())
                .build();
    }
}