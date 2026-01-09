package test.practicetest.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.practicetest.spring.api.service.order.dto.OrderCreateRequest;
import test.practicetest.spring.api.service.order.dto.OrderCreateResponse;
import test.practicetest.spring.domain.order.OrderRepository;
import test.practicetest.spring.domain.product.Product;
import test.practicetest.spring.domain.product.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderCreateResponse createOrder(OrderCreateRequest orderDto) {
        List<String> productNumbers = orderDto.getProductNumbers();

        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        return null;
    }
}
