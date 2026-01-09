package test.practicetest.spring.api.service.order.dto;

import lombok.Getter;
import test.practicetest.spring.api.service.product.dto.ProductDto;
import test.practicetest.spring.domain.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderCreateResponse {
    private Long id;
    private OrderStatus status;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductDto> products;
}
