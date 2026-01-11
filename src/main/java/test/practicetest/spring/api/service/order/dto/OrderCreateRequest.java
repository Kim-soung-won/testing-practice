package test.practicetest.spring.api.service.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.practicetest.spring.api.service.product.dto.ProductDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {
    private List<String> productNumbers;

    @Builder
    public OrderCreateRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
}
