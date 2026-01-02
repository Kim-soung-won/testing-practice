package test.practicetest.spring.api.service.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.practicetest.spring.domain.product.Product;
import test.practicetest.spring.domain.product.ProductSellingStatus;
import test.practicetest.spring.domain.product.ProductType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDto {
    private Long id;
    private String productNumber;
    private ProductType productType;
    private ProductSellingStatus sellingStatus;
    private int price;
    private String name;

    public ProductDto(Product entity) {
        this.id = entity.getId();
        this.productNumber = entity.getProductNumber();
        this.productType = entity.getType();
        this.sellingStatus = entity.getSellingStatus();
        this.price = entity.getPrice();
        this.name = entity.getName();
    }
}
