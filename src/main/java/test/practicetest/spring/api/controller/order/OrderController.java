package test.practicetest.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.practicetest.spring.api.service.order.dto.OrderCreateRequest;
import test.practicetest.spring.api.service.order.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public void createOrder(@RequestBody OrderCreateRequest dto) {
        orderService.createOrder(dto);

    }
}
