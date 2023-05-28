package com.ibx.grid_core_test.api.rest.controller;

import com.ibx.grid_core_test.api.dto.DefaultErrorResponse;
import com.ibx.grid_core_test.api.dto.GetPriceByProductResponse;
import com.ibx.grid_core_test.application.GridCoreTestApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = GridCoreTestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ProductPriceControllerIT {

    private static final String COMMON_PATH = "http://localhost:%d/%s";

    private static final String GET_PRODUCT_PRICE_PATH = "ibx/1/product/%d/price";

    private static final String BRAND_ID_PARAM = "?brandId=%s";

    private static final String APPLICATION_DATE_PARAM = "%sapplicationDate=%s";

    private static final Long PRODUCT_ID = 35455L;

    private static final Long PRODUCT_ID_NOT_FOUND = -1L;

    private static final Integer BRAND_ID = 1;

    private static final LocalDateTime DEFAULT_APPLICATION_DATE = LocalDateTime.now();

    private static final LocalDateTime APPLICATION_DATE_TEST_1 = LocalDateTime.parse("2020-06-14T10:00:00");

    private static final LocalDateTime APPLICATION_DATE_TEST_2 = LocalDateTime.parse("2020-06-14T16:00:00");

    private static final LocalDateTime APPLICATION_DATE_TEST_3 = LocalDateTime.parse("2020-06-14T21:00:00");

    private static final LocalDateTime APPLICATION_DATE_TEST_4 = LocalDateTime.parse("2020-06-15T10:00:00");

    private static final LocalDateTime APPLICATION_DATE_TEST_5 = LocalDateTime.parse("2020-06-16T21:00:00");

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Get price by product when brand id is null")
    void getPriceByProduct_whenBrandIdIsNull_shouldReturnExpectedError() {
        final var result = restTemplate.getForEntity(
                getProductPricePath(PRODUCT_ID, null, DEFAULT_APPLICATION_DATE), Object.class);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Get price by product when application date is null")
    void getPriceByProduct_whenApplicationDateIsNull_shouldReturnExpectedError() {
        final var result = restTemplate.getForEntity(
                getProductPricePath(PRODUCT_ID, BRAND_ID, null), Object.class);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Get price by product when product price not found")
    void getPriceByProduct_whenProductPriceNotFound_shouldReturnExpectedError() {
        final var result = restTemplate.getForEntity(
                getProductPricePath(PRODUCT_ID_NOT_FOUND, BRAND_ID, APPLICATION_DATE_TEST_1), DefaultErrorResponse.class);

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        final DefaultErrorResponse defaultErrorResponse = result.getBody();
        assertNotNull(defaultErrorResponse);
        assertEquals("Product price not found", defaultErrorResponse.getMessage());
    }

    // REQUIRED TESTS

    /**
     * Test 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     */
    @Test
    @DisplayName("Get price by product when test 1")
    void getPriceByProduct_whenTest1_shouldReturnExpectedProductPrice() {
        final var result = restTemplate.getForEntity(
                getProductPricePath(PRODUCT_ID, BRAND_ID, APPLICATION_DATE_TEST_1), GetPriceByProductResponse.class);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        final GetPriceByProductResponse getPriceByProductResponse = result.getBody();
        assertNotNull(getPriceByProductResponse);
        assertEquals(1, getPriceByProductResponse.getPriceList());
        assertEquals(PRODUCT_ID, getPriceByProductResponse.getProductId());
        assertEquals(BRAND_ID, getPriceByProductResponse.getBrandId());
        assertEquals(LocalDateTime.parse("2020-06-14T00:00:00"), getPriceByProductResponse.getStartDate());
        assertEquals(LocalDateTime.parse("2020-12-31T23:59:59"), getPriceByProductResponse.getEndDate());
        assertEquals(35.5, getPriceByProductResponse.getPrice());
    }

    /**
     * Test 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     */
    @Test
    @DisplayName("Get price by product when test 2")
    void getPriceByProduct_whenTest2_shouldReturnExpectedProductPrice() {
        final var result = restTemplate.getForEntity(
                getProductPricePath(PRODUCT_ID, BRAND_ID, APPLICATION_DATE_TEST_2), GetPriceByProductResponse.class);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        final GetPriceByProductResponse getPriceByProductResponse = result.getBody();
        assertNotNull(getPriceByProductResponse);
        assertEquals(2, getPriceByProductResponse.getPriceList());
        assertEquals(PRODUCT_ID, getPriceByProductResponse.getProductId());
        assertEquals(BRAND_ID, getPriceByProductResponse.getBrandId());
        assertEquals(LocalDateTime.parse("2020-06-14T15:00:00"), getPriceByProductResponse.getStartDate());
        assertEquals(LocalDateTime.parse("2020-06-14T18:30:00"), getPriceByProductResponse.getEndDate());
        assertEquals(25.45, getPriceByProductResponse.getPrice());
    }

    /**
     * Test 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     */
    @Test
    @DisplayName("Get price by product when test 3")
    void getPriceByProduct_whenTest3_shouldReturnExpectedProductPrice() {
        final var result = restTemplate.getForEntity(
                getProductPricePath(PRODUCT_ID, BRAND_ID, APPLICATION_DATE_TEST_3), GetPriceByProductResponse.class);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        final GetPriceByProductResponse getPriceByProductResponse = result.getBody();
        assertNotNull(getPriceByProductResponse);
        assertEquals(1, getPriceByProductResponse.getPriceList());
        assertEquals(PRODUCT_ID, getPriceByProductResponse.getProductId());
        assertEquals(BRAND_ID, getPriceByProductResponse.getBrandId());
        assertEquals(LocalDateTime.parse("2020-06-14T00:00:00"), getPriceByProductResponse.getStartDate());
        assertEquals(LocalDateTime.parse("2020-12-31T23:59:59"), getPriceByProductResponse.getEndDate());
        assertEquals(35.5, getPriceByProductResponse.getPrice());
    }

    /**
     * Test 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)
     */
    @Test
    @DisplayName("Get price by product when test 4")
    void getPriceByProduct_whenTest4_shouldReturnExpectedProductPrice() {
        final var result = restTemplate.getForEntity(
                getProductPricePath(PRODUCT_ID, BRAND_ID, APPLICATION_DATE_TEST_4), GetPriceByProductResponse.class);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        final GetPriceByProductResponse getPriceByProductResponse = result.getBody();
        assertNotNull(getPriceByProductResponse);
        assertEquals(3, getPriceByProductResponse.getPriceList());
        assertEquals(PRODUCT_ID, getPriceByProductResponse.getProductId());
        assertEquals(BRAND_ID, getPriceByProductResponse.getBrandId());
        assertEquals(LocalDateTime.parse("2020-06-15T00:00:00"), getPriceByProductResponse.getStartDate());
        assertEquals(LocalDateTime.parse("2020-06-15T11:00:00"), getPriceByProductResponse.getEndDate());
        assertEquals(30.5, getPriceByProductResponse.getPrice());
    }

    /**
     * Test 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)
     */
    @Test
    @DisplayName("Get price by product when test 5")
    void getPriceByProduct_whenTest5_shouldReturnExpectedProductPrice() {
        final var result = restTemplate.getForEntity(
                getProductPricePath(PRODUCT_ID, BRAND_ID, APPLICATION_DATE_TEST_5), GetPriceByProductResponse.class);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        final GetPriceByProductResponse getPriceByProductResponse = result.getBody();
        assertNotNull(getPriceByProductResponse);
        assertEquals(4, getPriceByProductResponse.getPriceList());
        assertEquals(PRODUCT_ID, getPriceByProductResponse.getProductId());
        assertEquals(BRAND_ID, getPriceByProductResponse.getBrandId());
        assertEquals(LocalDateTime.parse("2020-06-15T16:00:00"), getPriceByProductResponse.getStartDate());
        assertEquals(LocalDateTime.parse("2020-12-31T23:59:59"), getPriceByProductResponse.getEndDate());
        assertEquals(38.95, getPriceByProductResponse.getPrice());
    }

    private String getProductPricePath(final Long productId, final Integer brandId, final LocalDateTime applicationDate) {
        final String getProductPricePath = String.format(GET_PRODUCT_PRICE_PATH, productId);

        String absolutePath = String.format(COMMON_PATH, port, getProductPricePath);

        if (brandId != null) {
            absolutePath = absolutePath.concat(buildBrandIdParam(brandId));
        }

        if (applicationDate != null) {
            absolutePath = absolutePath.concat(buildApplicationDateParam(applicationDate, brandId));
        }

        return absolutePath;
    }

    private String buildBrandIdParam(final Integer brandId) {
        return String.format(BRAND_ID_PARAM, brandId);
    }

    private String buildApplicationDateParam(final LocalDateTime applicationDate, final Integer brandId) {
        if (brandId != null) {
            return String.format(APPLICATION_DATE_PARAM, "&", applicationDate);
        }
        return String.format(APPLICATION_DATE_PARAM, "?", applicationDate);
    }
}