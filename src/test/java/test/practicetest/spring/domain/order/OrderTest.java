package test.practicetest.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import test.practicetest.spring.domain.product.Product;
import test.practicetest.spring.domain.product.ProductSellingStatus;
import test.practicetest.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static test.practicetest.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
@SpringBootTest
class OrderTest {

    @DisplayName("주문 생성시 상품 리스트에서 주문의 총 금액(totalPrice)를 계산한다.")
    @Test
    void calculateTotalPrice(){
        //given
        LocalDateTime now = LocalDateTime.now();
        Product product1 = settingProduct("001", 1000);
        Product product2 = settingProduct("002", 2000);
        Product product3 = settingProduct("003", 3000);
        List<Product> products = List.of(product1, product2, product3);

        //when
        Order order = new Order(products, now);

        //then
        assertThat(order.getTotalPrice()).isEqualTo(6000);
    }

    @DisplayName("주문 생성시 주문 상태는 INIT 이다.")
    @Test
    void initOrder(){
        //given
        LocalDateTime now = LocalDateTime.now();
        Product product1 = settingProduct("001", 1000);
        Product product2 = settingProduct("002", 2000);
        Product product3 = settingProduct("003", 3000);
        List<Product> products = List.of(product1, product2, product3);

        //when
        Order order = new Order(products, now);

        //then
        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }

    @DisplayName("주문 생성시 주문 등록 시간을 기록한다..")
    @Test
    void registeredDateTime(){
        //given
        LocalDateTime now = LocalDateTime.now();
        Product product1 = settingProduct("001", 1000);
        Product product2 = settingProduct("002", 2000);
        Product product3 = settingProduct("003", 3000);
        List<Product> products = List.of(product1, product2, product3);

        //when
        Order order = new Order(products, now);

        //then
        assertThat(order.getRegisteredDateTime()).isEqualTo(now);
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