package test.practicetest.spring.api.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import test.practicetest.spring.api.service.product.ProductService;
import test.practicetest.spring.api.service.product.dto.ProductDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/v1/products/selling")
    public List<ProductDto> getSellingProducts() {
        return productService.getSellingProducts();
    }
}
