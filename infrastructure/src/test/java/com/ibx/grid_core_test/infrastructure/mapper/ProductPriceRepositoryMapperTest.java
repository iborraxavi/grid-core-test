package com.ibx.grid_core_test.infrastructure.mapper;

import com.ibx.grid_core_test.infrastructure.model.ProductPriceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductPriceRepositoryMapperTest {

    private static final Long ID = 9999999L;

    private static final Long PRICE_LIST = 111111111L;

    private static final Long PRODUCT_ID = 222222222L;

    private static final Integer BRAND_ID = 1;

    private static final LocalDateTime START_DATE = LocalDateTime.now().minusDays(2);

    private static final LocalDateTime END_DATE = LocalDateTime.now().plusDays(2);

    private static final Integer PRIORITY = 3;

    private static final Double PRICE = 30.08;

    private static final String CURRENCY = "EUR";

    @InjectMocks
    private ProductPriceRepositoryMapper productPriceRepositoryMapper;

    @Test
    @DisplayName("Map product price when product price entity is null")
    void mapProductPrice_whenProductPriceEntityIsNull_shouldReturnNull() {
        final var result = productPriceRepositoryMapper.mapProductPrice(null);

        assertNull(result);
    }

    @Test
    @DisplayName("Map product price when full data")
    void mapProductPrice_whenFullData_shouldReturnFullData() {
        final ProductPriceEntity productPriceEntity = getProductPriceEntity();

        final var result = productPriceRepositoryMapper.mapProductPrice(productPriceEntity);

        assertNotNull(result);
        assertEquals(ID, result.getId());
        assertEquals(BRAND_ID, result.getBrandId());
        assertEquals(START_DATE, result.getStartDate());
        assertEquals(END_DATE, result.getEndDate());
        assertEquals(PRICE_LIST, result.getPriceList());
        assertEquals(PRODUCT_ID, result.getProductId());
        assertEquals(PRIORITY, result.getPriority());
        assertEquals(PRICE, result.getPrice());
        assertEquals(CURRENCY, result.getCurrency());
    }

    private ProductPriceEntity getProductPriceEntity() {
        return ProductPriceEntity.builder()
                .id(ID)
                .brandId(BRAND_ID)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .priceList(PRICE_LIST)
                .productId(PRODUCT_ID)
                .priority(PRIORITY)
                .price(PRICE)
                .currency(CURRENCY)
                .build();
    }
}
