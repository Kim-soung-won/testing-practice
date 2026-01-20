package test.practicetest.spring.api.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import test.practicetest.spring.client.mail.MailSendClient;
import test.practicetest.spring.domain.history.mail.MailSendHistory;
import test.practicetest.spring.domain.history.mail.MailSendHistoryRepository;
import test.practicetest.spring.domain.order.Order;
import test.practicetest.spring.domain.order.OrderRepository;
import test.practicetest.spring.domain.orderproduct.OrderProductRepository;
import test.practicetest.spring.domain.product.Product;
import test.practicetest.spring.domain.product.ProductRepository;
import test.practicetest.spring.domain.product.ProductSellingStatus;
import test.practicetest.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static test.practicetest.spring.domain.product.ProductType.HANDMADE;

@SpringBootTest
@ActiveProfiles("test")
class OrderStatisticsServiceTest {
    @Autowired
    private  OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    private static final LocalDateTime now = LocalDateTime.of(2023, 3, 5, 0, 0);

    @MockitoBean
    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제 완료 주문들을 읽어 매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticsMail(){
        //given
        Product product1 = settingProduct("001", "아메리카노", ProductSellingStatus.SELLING, HANDMADE, 4000);
        Product product2 = settingProduct("002", "카페라떼", ProductSellingStatus.HOLD, HANDMADE, 4500);
        Product product3 = settingProduct("003", "팥빙수", ProductSellingStatus.STOP_SELLING, HANDMADE, 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        orderRepository.save(new Order(List.of(product1, product2, product3), now));

        Order order1 = createPaymentCompletedOrder(List.of(product1, product2), now.minusMinutes(1L));
        Order order2 = createPaymentCompletedOrder(List.of(product1, product2), now.minusMinutes(5L));
        Order order3 = createPaymentCompletedOrder(List.of(product1, product2), now.plusMinutes(1L));
        Order order4 = createPaymentCompletedOrder(List.of(product1, product2), now.plusDays(1L));

        // MockBean을 통해 주입된 mailSendClient의 sendEmail 메서드가 호출되면 어떤 로직을 실행할지 설정한다.
        // 아래는 String 값 어느것이든 4개의 인자가 모두 들어가면 true를 반환한다고 설정한다.
        Mockito.when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);

        //when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(now.toLocalDate(), "test@test.com");

        //then
        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계: 17000");
    }

    private Order createPaymentCompletedOrder(List<Product> products, LocalDateTime registeredDateTime) {
        Order order = Order.builder()
                .products(products)
                .orderStatus(test.practicetest.spring.domain.order.OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(registeredDateTime)
                .build();
        return orderRepository.save(order);
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