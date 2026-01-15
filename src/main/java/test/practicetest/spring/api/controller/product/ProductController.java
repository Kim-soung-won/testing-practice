package test.practicetest.spring.api.controller.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import test.practicetest.spring.api.ApiResponse;
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
    public ApiResponse<ProductDto> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        ProductDto result = productService.create(request);
        return ApiResponse.of(HttpStatus.OK, result);
    }

    @GetMapping("/selling")
    public ApiResponse<List<ProductDto>> getSellingProducts() {

        return ApiResponse.of(HttpStatus.OK,productService.getSellingProducts());
    }
}
