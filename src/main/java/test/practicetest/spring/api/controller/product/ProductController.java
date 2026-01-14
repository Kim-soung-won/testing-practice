package test.practicetest.spring.api.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import test.practicetest.spring.api.controller.product.dto.ProductCreateRequest;
import test.practicetest.spring.api.service.product.ProductService;
import test.practicetest.spring.api.service.product.dto.ProductDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/new")
    public void createProduct(@RequestBody ProductCreateRequest request) {
        productService.create(request);

    }

    @GetMapping("/selling")
    public List<ProductDto> getSellingProducts() {
        return productService.getSellingProducts();
    }
}
