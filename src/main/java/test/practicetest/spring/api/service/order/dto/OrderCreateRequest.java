package test.practicetest.spring.api.service.order.dto;

import lombok.Builder;
import lombok.Getter;
import test.practicetest.spring.api.service.product.dto.ProductDto;

import java.util.List;

@Getter
public class OrderCreateRequest {
    private final List<String> productNumbers;

    @Builder
    public OrderCreateRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
}
