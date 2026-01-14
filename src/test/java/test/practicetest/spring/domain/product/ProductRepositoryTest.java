package test.practicetest.spring.domain.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static test.practicetest.spring.domain.product.ProductType.HANDMADE;

/**
 * SpringBootTest : 테스트시 Spring을 실행함
 * DataJpaTest : JPA관련 Bean만 사용해서 테스트하여 SpringBootTest보다 좀 빠르고
 *              Transactional이 기본으로 걸려있음
 */
@ActiveProfiles("test")
//@SpringBootTest
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp(){
        productRepository.deleteAll();

        Product product1 = settingProduct("001", "아메리카노", ProductSellingStatus.SELLING, HANDMADE, 4000);
        Product product2 = settingProduct("002", "카페라떼", ProductSellingStatus.HOLD, HANDMADE, 4500);
        Product product3 = settingProduct("003", "팥빙수", ProductSellingStatus.STOP_SELLING, HANDMADE, 7000);
        productRepository.saveAll(List.of(product1, product2, product3));
    }

    @DisplayName("원하는 판매 상태를 갖는 상품 목록 전체 조회")
    @Test
    void findAllBySellingStatusIn(){
        //given
        //when
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        //then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        tuple("002", "카페라떼", ProductSellingStatus.HOLD)
                );
    }

    @DisplayName("주문시 사용되는 상품번호 목록에 해당하는 상품 목록 조회")
    @Test
    void findByProductNumberIn(){
        //given
        //when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        //then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        tuple("002", "카페라떼", ProductSellingStatus.HOLD)
                );
    }

    @DisplayName("가장 마지막에 저장된 상품 번호를 조회한다. 상품 번호만")
    @Test
    void findByLatestProductNumber(){
        //given
        String targetProductNumber = "004";
        productRepository.save(settingProduct(targetProductNumber, "와플", ProductSellingStatus.SELLING, HANDMADE, 5000));

        //when
        String productNumber = productRepository.findLatestProductNumber();

        //then
        assertThat(productNumber).isEqualTo(targetProductNumber);
    }

    private Product settingProduct(String productNumber, String name, ProductSellingStatus sellingStatus, ProductType type, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .price(price)
                .name(name)
                .sellingStatus(sellingStatus)
                .build();
    }
}