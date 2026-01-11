package test.practicetest.spring.api.service.order;

import jakarta.transaction.Transactional;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import test.practicetest.spring.api.service.order.dto.OrderCreateRequest;
import test.practicetest.spring.api.service.order.dto.OrderCreateResponse;
import test.practicetest.spring.domain.order.OrderRepository;
import test.practicetest.spring.domain.orderproduct.OrderProductRepository;
import test.practicetest.spring.domain.product.Product;
import test.practicetest.spring.domain.product.ProductRepository;
import test.practicetest.spring.domain.product.ProductSellingStatus;
import test.practicetest.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @DisplayName("주문 번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrder(){
        //given
        LocalDateTime now = LocalDateTime.now();
        Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, 4000, "아메리카노");
        Product product2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, 4500, "카페라떼");
        Product product3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, 7000, "팥빙수");
        productRepository.saveAll(List.of(product1, product2, product3));

        List<String> createRequests = List.of("001", "002");

        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productNumbers(createRequests)
                .build();

        //when
        OrderCreateResponse orderCreateResponse = orderService.createOrder(orderCreateRequest, now);

        //then
        assertThat(orderCreateResponse.getId()).isNotNull();
        assertThat(orderCreateResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(now, 8500);
        assertThat(orderCreateResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", 4000),
                        Tuple.tuple("002", 4500)
                );

    }

    @DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
    @Test
    void createOrderWithDuplicateProductNumbers(){
        //given
        LocalDateTime now = LocalDateTime.now();
        Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, 4000, "아메리카노");
        Product product2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, 4500, "카페라떼");
        Product product3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, 7000, "팥빙수");
        productRepository.saveAll(List.of(product1, product2, product3));

        List<String> createRequests = List.of("001", "001");

        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productNumbers(createRequests)
                .build();

        //when
        OrderCreateResponse orderCreateResponse = orderService.createOrder(orderCreateRequest, now);

        //then
        assertThat(orderCreateResponse.getId()).isNotNull();
        assertThat(orderCreateResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(now, 8000);
        assertThat(orderCreateResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", 4000),
                        Tuple.tuple("001", 4000)
                );

    }




    private Product createProduct(String productNumber, ProductType type, ProductSellingStatus status, int price, String name) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(status)
                .price(price)
                .name(name)
                .build();
    }

}