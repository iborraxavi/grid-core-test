package com.ibx.grid_core_test.api.rest.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPriceByProductResponse {

    private Long rateId;

    private Long productId;

    private Integer brandId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private BigDecimal price;
}