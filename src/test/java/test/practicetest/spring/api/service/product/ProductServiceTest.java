package test.practicetest.spring.api.service.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import test.practicetest.spring.api.controller.product.dto.ProductCreateRequest;
import test.practicetest.spring.api.service.product.dto.ProductDto;
import test.practicetest.spring.domain.product.Product;
import test.practicetest.spring.domain.product.ProductRepository;
import test.practicetest.spring.domain.product.ProductSellingStatus;
import test.practicetest.spring.domain.product.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static test.practicetest.spring.domain.product.ProductType.HANDMADE;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("신규 상품을 등록한다. 상품 번호는 가장 최근 상품번호의 1 증가한 값")
    @Test
    void createProduct(){
        //given
        Product product1 = settingProduct("001", "아메리카노", ProductSellingStatus.SELLING, HANDMADE, 4000);
        Product product2 = settingProduct("002", "카페라떼", ProductSellingStatus.HOLD, HANDMADE, 4500);
        Product product3 = settingProduct("003", "팥빙수", ProductSellingStatus.STOP_SELLING, HANDMADE, 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아이스티")
                .price(5000)
                .build();

        //when
        ProductDto response = productService.create(request);

        //then
        assertThat(response).extracting("productNumber", "productType", "sellingStatus", "name", "price")
                .contains("004", HANDMADE, ProductSellingStatus.SELLING, "아이스티", 5000);
    }

    @DisplayName("신규 상품을 등록한다. 최초 상품이라면 그 상품 번호는 '001' 이다.")
    @Test
    void createProductIfProductsEmptyProductNumberIs001(){
        //given
        Product product1 = settingProduct("001", "아메리카노", ProductSellingStatus.SELLING, HANDMADE, 4000);
        Product product2 = settingProduct("002", "카페라떼", ProductSellingStatus.HOLD, HANDMADE, 4500);
        Product product3 = settingProduct("003", "팥빙수", ProductSellingStatus.STOP_SELLING, HANDMADE, 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        productRepository.deleteAll();

        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아이스티")
                .price(5000)
                .build();

        //when
        ProductDto response = productService.create(request);

        //then
        assertThat(response).extracting("productNumber", "productType", "sellingStatus", "name", "price")
                .contains("001", HANDMADE, ProductSellingStatus.SELLING, "아이스티", 5000);
    }

    private Product settingProduct(String productNumber, String name, ProductSellingStatus sellingStatus, ProductType type, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .price(price)
                .name(name)
                .sellingStatus(sellingStatus)
                .build();
    }

}