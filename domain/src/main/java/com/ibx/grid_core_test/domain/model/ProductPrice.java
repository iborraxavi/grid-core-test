package com.ibx.grid_core_test.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductPrice {

    private final Long id;

    private final Integer brandId;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

    private final Long priceList;

    private final Long productId;

    private final Integer priority;

    private Double price;

    private String currency;
}