package test.practicetest.spring.api.service.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.practicetest.spring.api.service.product.dto.ProductDto;
import test.practicetest.spring.domain.order.Order;
import test.practicetest.spring.domain.order.OrderStatus;
import test.practicetest.spring.domain.orderproduct.OrderProduct;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateResponse {
    private Long id;
    private OrderStatus status;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductDto> products;

    public static OrderCreateResponse of(Order order){
        return OrderCreateResponse.builder()
                .id(order.getId())
                .status(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .registeredDateTime(order.getRegisteredDateTime())
                .products(order.getOrderProducts().stream()
                        .map(orderProduct -> new ProductDto(orderProduct.getProduct()))
                        .toList())
                .build();
    }
}
