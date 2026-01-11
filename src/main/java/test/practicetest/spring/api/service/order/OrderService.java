package test.practicetest.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.practicetest.spring.api.service.order.dto.OrderCreateRequest;
import test.practicetest.spring.api.service.order.dto.OrderCreateResponse;
import test.practicetest.spring.domain.order.Order;
import test.practicetest.spring.domain.order.OrderRepository;
import test.practicetest.spring.domain.product.Product;
import test.practicetest.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderCreateResponse createOrder(OrderCreateRequest orderDto, LocalDateTime registeredDateTime) {
        List<String> productNumbers = orderDto.getProductNumbers();

        List<Product> products = findProductsBy(productNumbers);

        Order order = new Order(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderCreateResponse.of(savedOrder);
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Map<String, Product> productMap = products.stream().collect(Collectors.toMap(
                Product::getProductNumber, p -> p)
        );
        return productNumbers.stream()
                .map(productMap::get)
                .toList();
    }
}
