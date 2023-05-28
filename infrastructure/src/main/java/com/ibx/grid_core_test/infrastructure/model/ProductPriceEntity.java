package com.ibx.grid_core_test.infrastructure.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@Table(name = "PRODUCT_PRICES")
public class ProductPriceEntity {

    @Id
    private final Long id;

    @Column(value = "BRAND_ID")
    private final Integer brandId;

    @Column(value = "START_DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final LocalDateTime startDate;

    @Column(value = "END_DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final LocalDateTime endDate;

    @Column(value = "PRICE_LIST")
    private final Long priceList;

    @Column(value = "PRODUCT_ID")
    private final Long productId;

    private final Integer priority;

    private Double price;

    private String currency;
}
