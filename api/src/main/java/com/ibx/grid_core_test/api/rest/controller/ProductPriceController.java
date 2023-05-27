package com.ibx.grid_core_test.api.rest.controller;

import com.ibx.grid_core_test.api.rest.mapper.ProductPriceRestMapper;
import com.ibx.grid_core_test.api.rest.model.response.GetPriceByProductResponse;
import com.ibx.grid_core_test.domain.use_cases.GetPriceByProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/ibx/1/product")
public class ProductPriceController {

    @Autowired
    private ProductPriceRestMapper productPriceRestMapper;

    @Autowired
    private GetPriceByProductUseCase getPriceByProductUseCase;

    @GetMapping("/{productId}/price")
    public Mono<ResponseEntity<GetPriceByProductResponse>> getPriceByProduct(@PathVariable(value = "productId") final Long productId,
                                                                             @RequestParam(value = "brandId") final Integer brandId,
                                                                             @RequestParam(value = "applicationDate")
                                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime applicationDate) {
        return getPriceByProductUseCase.dispatch(productId, brandId, applicationDate)
                .map(productPriceRestMapper::mapGetPriceByProductResponse)
                .map(getPriceByProductResponse -> new ResponseEntity<>(getPriceByProductResponse, HttpStatus.OK));
    }
}
