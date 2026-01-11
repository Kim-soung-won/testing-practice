package test.practicetest.spring.domain.stock;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class StockTest {

    @DisplayName("재고의 수량이 주문 수량보다 적을 경우 True 를 반환한다.")
    @Test
    void quantityLessThan(){
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;
        //when
        boolean result = stock.isQuantityLessThan(quantity);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("재고를 주어진 갯수 만큼 차감할 수 있다.")
    @Test
    void deductQuantity(){
        //given
        Stock stock = Stock.create("001", 5);
        int quantity = 3;

        //when
        stock.deductQuantity(quantity);

        //then
        assertThat(stock.getQuantity()).isEqualTo(2);
    }

    @DisplayName("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.")
    @Test
    void deductQuantity2(){
        //given
        Stock stock = Stock.create("001", 5);
        int quantity = 10;

        //when
        stock.deductQuantity(quantity);

        //then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 재고 수량이 없습니다.");
    }


}