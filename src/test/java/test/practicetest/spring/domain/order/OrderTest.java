package test.practicetest.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import test.practicetest.spring.domain.product.Product;
import test.practicetest.spring.domain.product.ProductSellingStatus;
import test.practicetest.spring.domain.product.ProductType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static test.practicetest.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
@SpringBootTest
class OrderTest {

    @DisplayName("상품 리스트에서 주문의 총 금액(totalPrice)를 계산한다.")
    @Test
    void calculateTotalPrice(){
        //given
        Product product1 = settingProduct("001", 1000);
        Product product2 = settingProduct("002", 2000);
        Product product3 = settingProduct("003", 3000);
        List<Product> products = List.of(product1, product2, product3);

        Order order = new Order();


        //when

        //then
    }

    private Product settingProduct(String productNumber, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(HANDMADE)
                .price(price)
                .name("메뉴 명")
                .sellingStatus(ProductSellingStatus.SELLING)
                .build();
    }
}