package com.ibx.grid_core_test.api.rest.controller;

import com.ibx.grid_core_test.api.dto.GetPriceByProductResponse;
import com.ibx.grid_core_test.api.rest.mapper.ProductPriceRestMapper;
import com.ibx.grid_core_test.api.v1.ProductPricesApi;
import com.ibx.grid_core_test.domain.use_cases.GetPriceByProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
public class ProductPricesController implements ProductPricesApi {

    @Autowired
    private ProductPriceRestMapper productPriceRestMapper;

    @Autowired
    private GetPriceByProductUseCase getPriceByProductUseCase;

    @Override
    public Mono<ResponseEntity<GetPriceByProductResponse>> getProductPrice(final Long productId, final Integer brandId,
                                                                           final LocalDateTime applicationDate, final ServerWebExchange exchange) {
        return getPriceByProductUseCase.dispatch(productId, brandId, applicationDate)
                .map(productPriceRestMapper::mapGetPriceByProductResponse)
                .map(getPriceByProductResponse -> new ResponseEntity<>(getPriceByProductResponse, HttpStatus.OK));
    }
}