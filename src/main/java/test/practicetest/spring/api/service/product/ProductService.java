package test.practicetest.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.practicetest.spring.api.controller.product.dto.ProductCreateRequest;
import test.practicetest.spring.api.service.product.dto.ProductDto;
import test.practicetest.spring.domain.product.Product;
import test.practicetest.spring.domain.product.ProductRepository;
import test.practicetest.spring.domain.product.ProductSellingStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDto> getSellingProducts() {
        List<Product> productList = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());
        return productList.stream().map(
                ProductDto::new
        ).toList();
    }

    public ProductDto create(ProductCreateRequest request) {

        Product product = request.toEntity(createNextProductNumber());

        return new ProductDto(productRepository.save(product));
    }

    private String createNextProductNumber(){
        String latestProductNumber = productRepository.findLatestProductNumber();
        if(latestProductNumber == null){
            return "001";
        }

        int latestProjectNumber = Integer.parseInt(latestProductNumber);
        int nextProductNumber = latestProjectNumber + 1;
        return String.format("%03d", nextProductNumber);
    }
}
