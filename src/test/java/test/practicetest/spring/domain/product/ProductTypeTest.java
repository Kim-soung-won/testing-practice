package test.practicetest.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType(){
        //given
        ProductType productTypeHandMade = ProductType.HANDMADE;
        ProductType productTypeBottle = ProductType.BOTTLE;

        //when
        boolean falseResult = ProductType.containsStockType(productTypeHandMade);
        boolean trueResult = ProductType.containsStockType(productTypeBottle);

        //then
        assertThat(falseResult).isFalse();
        assertThat(trueResult).isTrue();
    }

}