package test.practicetest.unit;

import org.junit.jupiter.api.Test;
import test.practicetest.unittest.CafeKiosk;
import test.practicetest.unittest.beverage.Americano;
import test.practicetest.unittest.beverage.Beverage;
import test.practicetest.unittest.beverage.Latte;
import test.practicetest.unittest.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CafeKioskTest {

    @Test
    void add(){
        CafeKiosk kiosk = new CafeKiosk();
        kiosk.add(new Americano());

        assertThat(kiosk.getBeverages().size()).isEqualTo(1);
        assertThat(kiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void addSeveralBeverages(){
        CafeKiosk kiosk = new CafeKiosk();
        Americano americano = new Americano();
        kiosk.add(americano, 2);

        assertThat(kiosk.getBeverages().size()).isEqualTo(2);
        assertThat(kiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
        assertThat(kiosk.getBeverages().get(1).getName()).isEqualTo("아메리카노");
    }

    @Test
    void addZeroBeverages(){
        CafeKiosk kiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> kiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("0잔 이하 주문은 불가능합니다.");
    }

    @Test
    void remove(){
        CafeKiosk kiosk = new CafeKiosk();
        Beverage americano = new Americano();
        kiosk.add(americano);

        assertThat(kiosk.getBeverages().size()).isEqualTo(1);

        kiosk.deleteBeverage(americano);
        assertThat(kiosk.getBeverages().size()).isEqualTo(0);
    }

    @Test
    void clear() {
        CafeKiosk kiosk = new CafeKiosk();
        Beverage americano = new Americano();
        kiosk.add(americano);

        assertThat(kiosk.getBeverages().size()).isEqualTo(1);

        kiosk.add(new Latte());
        assertThat(kiosk.getBeverages().size()).isEqualTo(2);

        kiosk.clear();
        assertThat(kiosk.getBeverages().size()).isEqualTo(0);
    }

    @Test
    void calculateTotalPrice() {
        CafeKiosk kiosk = new CafeKiosk();
        Beverage americano = new Americano();
        Beverage latte = new Latte();
        kiosk.add(americano, 2);
        kiosk.add(latte, 1);

        int totalPrice = kiosk.calculateTotalPrice();

        assertThat(totalPrice).isEqualTo(10000);
    }


    @Test
    void createOrderWithInsideOpenTime() {
        CafeKiosk kiosk = new CafeKiosk();
        Beverage americano = new Americano();
        kiosk.add(americano, 1);

        Order order = kiosk.createOrder(LocalDateTime.of(2023, 1, 17, 10, 0, 0));

        assertThat(order.getBeverages().size()).isEqualTo(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void createOrderWithOutsideOpenTime() {
        CafeKiosk kiosk = new CafeKiosk();
        Beverage americano = new Americano();
        kiosk.add(americano, 1);

        assertThatThrownBy(() -> kiosk.createOrder(LocalDateTime.of(2023, 1, 17, 9, 59, 59)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요");
    }
}
