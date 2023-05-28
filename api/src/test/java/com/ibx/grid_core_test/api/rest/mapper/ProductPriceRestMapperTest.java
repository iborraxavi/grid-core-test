package com.ibx.grid_core_test.api.rest.mapper;

import com.ibx.grid_core_test.domain.model.ProductPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductPriceRestMapperTest {

    private static final Long PRICE_LIST = 111111111L;

    private static final Long PRODUCT_ID = 222222222L;

    private static final Integer BRAND_ID = 1;

    private static final LocalDateTime START_DATE = LocalDateTime.now().minusDays(2);

    private static final LocalDateTime END_DATE = LocalDateTime.now().plusDays(2);

    private static final Double PRICE = 30.08;

    @InjectMocks
    private ProductPriceRestMapper productPriceRestMapper;

    @Test
    @DisplayName("Map get price by product response when null product price entity")
    void mapGetPriceByProductResponse_whenNullProductPriceEntity_shouldReturnNull() {
        final var result = productPriceRestMapper.mapGetPriceByProductResponse(null);

        assertNull(result);
    }

    @Test
    @DisplayName("Map get price by product response when full data")
    void mapGetPriceByProductResponse_whenFullData_shouldReturnNull() {
        final var result = productPriceRestMapper.mapGetPriceByProductResponse(getProductPriceEntity());

        assertNotNull(result);
        assertEquals(PRICE_LIST, result.getPriceList());
        assertEquals(PRODUCT_ID, result.getProductId());
        assertEquals(BRAND_ID, result.getBrandId());
        assertEquals(START_DATE, result.getStartDate());
        assertEquals(END_DATE, result.getEndDate());
        assertEquals(PRICE, result.getPrice());
    }

    private ProductPrice getProductPriceEntity() {
        return ProductPrice.builder()
                .priceList(PRICE_LIST)
                .productId(PRODUCT_ID)
                .brandId(BRAND_ID)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .price(PRICE)
                .build();
    }
}
