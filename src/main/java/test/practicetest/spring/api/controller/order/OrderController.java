package test.practicetest.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.practicetest.spring.api.service.order.dto.OrderCreateRequest;
import test.practicetest.spring.api.service.order.OrderService;
import test.practicetest.spring.api.service.order.dto.OrderCreateResponse;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public OrderCreateResponse createOrder(@RequestBody OrderCreateRequest dto) {
        LocalDateTime registeredDateTime = LocalDateTime.now();

        return orderService.createOrder(dto, registeredDateTime);
    }
}
