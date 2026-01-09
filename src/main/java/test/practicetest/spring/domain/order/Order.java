package test.practicetest.spring.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.practicetest.spring.domain.base.BaseEntity;
import test.practicetest.spring.domain.orderproduct.OrderProduct;
import test.practicetest.spring.domain.product.Product;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime registeredDateTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    public Order(List<Product> products) {
        this.orderStatus = OrderStatus.INIT;
        this.totalPrice = products.stream().mapToInt(Product::getPrice).sum();
    }

}
