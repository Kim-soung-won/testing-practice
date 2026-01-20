package test.practicetest.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.practicetest.spring.api.service.mail.MailService;
import test.practicetest.spring.domain.order.Order;
import test.practicetest.spring.domain.order.OrderRepository;
import test.practicetest.spring.domain.order.OrderStatus;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatisticsService {

    private final OrderRepository orderRepository;
    private final MailService mailService;

    public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {
        // 해당 일자에 결제완료된 주문들을 가져와서
        List<Order> orders = orderRepository.findOrdersByRegisteredDateTimeBetweenAndOrderStatus(
                orderDate.minusDays(1L).atStartOfDay(),
                orderDate.atStartOfDay(),
                OrderStatus.PAYMENT_COMPLETED
        );

        // 총 매출 합계를 계산하고
        int totalSales = orders.stream()
                .mapToInt(Order::getTotalPrice)
                .sum();

        // 메일 전송을 하고 싶다.
        boolean result = mailService.sendMail("TargetUserName", email, "주문 통계 메일", "총 매출 합계: " + totalSales);

        if(!result) {
            throw new RuntimeException("메일 전송에 실패했습니다.");
        }
        return true;
    }
}
