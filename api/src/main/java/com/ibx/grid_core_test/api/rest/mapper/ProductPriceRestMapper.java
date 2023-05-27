package com.ibx.grid_core_test.api.rest.mapper;

import com.ibx.grid_core_test.api.rest.model.response.GetPriceByProductResponse;
import com.ibx.grid_core_test.domain.model.ProductPriceEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceRestMapper {

    public GetPriceByProductResponse mapGetPriceByProductResponse(final ProductPriceEntity productPriceEntity) {
        if (productPriceEntity == null) {
            return null;
        }

        return GetPriceByProductResponse.builder()
                .rateId(productPriceEntity.getPriceList())
                .productId(productPriceEntity.getProductId())
                .brandId(productPriceEntity.getBrandId())
                .startDate(productPriceEntity.getStartDate())
                .endDate(productPriceEntity.getEndDate())
                .price(productPriceEntity.getPrice())
                .build();
    }
}