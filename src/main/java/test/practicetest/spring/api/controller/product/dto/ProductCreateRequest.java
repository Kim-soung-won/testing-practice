package test.practicetest.spring.api.controller.product.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "상품 타입은 필수이다. Product type is required.")
    private ProductType type;

    @NotNull(message = "상품 판매 상태는 필수이다. Product Selling Status is required.")
    private ProductSellingStatus sellingStatus;

    @NotBlank(message = "상품 이름은 필수이다. Product name is required.")
//    @NotBlank  NULL이 아닌것, 공백 문자열이나, "" 빈 문자열도 안된다.
//    @NotNull NULL이 아니면 된다, 공백 문자열이나, "" 빈 문자열은 허용된다.
//    @NotEmpty  NULL이 아닌것, 공백 문자열을 필터링한다.
    private String name;

    @Positive(message = "상품 가격은 양수여야 한다. Product price must be positive.")
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
