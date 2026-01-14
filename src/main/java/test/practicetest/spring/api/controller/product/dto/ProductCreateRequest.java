package test.practicetest.spring.api.controller.product.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.practicetest.spring.domain.product.Product;
import test.practicetest.spring.domain.product.ProductSellingStatus;
import test.practicetest.spring.domain.product.ProductType;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {
//    private String productNumber;
    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    public Product toEntity(String productNumber) {
        return Product.builder()
                .productNumber(productNumber)
                .type(this.type)
                .sellingStatus(this.sellingStatus)
                .name(this.name)
                .price(this.price)
                .build();
    }
}
