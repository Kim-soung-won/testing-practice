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
import test.practicetest.spring.domain.stock.Stock;
import test.practicetest.spring.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
//@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
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

    @DisplayName("재고와 관련있는 상품이 포함되어 있는 주문 번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrderWithStock(){
        //given
        LocalDateTime now = LocalDateTime.now();
        Product product1 = createProduct("001", ProductType.BAKERY, ProductSellingStatus.SELLING, 4000, "아메리카노");
        Product product2 = createProduct("002", ProductType.BAKERY, ProductSellingStatus.HOLD, 4500, "카페라떼");
        Product product3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, 7000, "팥빙수");
        productRepository.saveAll(List.of(product1, product2, product3));

        List<String> createRequests = List.of("001", "001", "002", "003");

        Stock stock1 = Stock.create("001", 2);
        Stock stock2 = Stock.create("002", 1);

        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productNumbers(createRequests)
                .build();

        //when
        OrderCreateResponse orderCreateResponse = orderService.createOrder(orderCreateRequest, now);

        //then
        assertThat(orderCreateResponse.getId()).isNotNull();
        assertThat(orderCreateResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(now, 19500);
        assertThat(orderCreateResponse.getProducts()).hasSize(4)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", 4000),
                        Tuple.tuple("001", 4000),
                        Tuple.tuple("002", 4500),
                        Tuple.tuple("003", 7000)
                );

        List<Stock> stocks = stockRepository.findAll();
        assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", 0),
                        Tuple.tuple("002", 0)
                );
    }

    @DisplayName("재고가 없는 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
    @Test
    void createOrderWithNoStock(){
        //given
        LocalDateTime now = LocalDateTime.now();
        Product product1 = createProduct("001", ProductType.BAKERY, ProductSellingStatus.SELLING, 4000, "아메리카노");
        Product product2 = createProduct("002", ProductType.BAKERY, ProductSellingStatus.HOLD, 4500, "카페라떼");
        Product product3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, 7000, "팥빙수");
        productRepository.saveAll(List.of(product1, product2, product3));

        List<String> createRequests = List.of("001", "001", "002", "003");

        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 1);

        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productNumbers(createRequests)
                .build();

        //when
        assertThatThrownBy(() -> orderService.createOrder(orderCreateRequest, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족한 상품이 있습니다.");
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