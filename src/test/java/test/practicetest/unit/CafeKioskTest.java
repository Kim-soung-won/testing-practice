package test.practicetest.unit;

import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("단일 낱개 음료 키오스크에서 담는 기능")
    @Test
    void add(){
        CafeKiosk kiosk = new CafeKiosk();
        kiosk.add(new Americano());

        assertThat(kiosk.getBeverages().size()).isEqualTo(1);
        assertThat(kiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("단일 음료 여러잔 한번에 키오스크에 추가")
    @Test
    void addSeveralBeverages(){
        CafeKiosk kiosk = new CafeKiosk();
        Americano americano = new Americano();
        kiosk.add(americano, 2);

        assertThat(kiosk.getBeverages().size()).isEqualTo(2);
        assertThat(kiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
        assertThat(kiosk.getBeverages().get(1).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("0개 이하의 음료 키오스크 추가시 Exception 발생")
    @Test
    void addZeroBeverages(){
        CafeKiosk kiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> kiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("0잔 이하 주문은 불가능합니다.");
    }

    @DisplayName("단일 음료 키오스크에서 제거하는 기능")
    @Test
    void remove(){
        CafeKiosk kiosk = new CafeKiosk();
        Beverage americano = new Americano();
        kiosk.add(americano);

        assertThat(kiosk.getBeverages().size()).isEqualTo(1);

        kiosk.deleteBeverage(americano);
        assertThat(kiosk.getBeverages().size()).isEqualTo(0);
    }

    @DisplayName("키오스크 담은 음료 초기화")
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

    @DisplayName("주문 목록에 담긴 음료들의 총 금액 계산")
    @Test
    void calculateTotalPrice() {
        // given
        CafeKiosk kiosk = new CafeKiosk();
        Beverage americano = new Americano();
        Beverage latte = new Latte();
        kiosk.add(americano, 2);
        kiosk.add(latte, 1);

        // when
        int totalPrice = kiosk.calculateTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(10000);
    }

    @DisplayName("BDD Template Intellij live Template 속성 설정 -> 'test' 자동완성 ")
    @Test
    void test(){
        //given

        //when

        //then
    }


    @DisplayName("영업시간 중 주문 실행")
    @Test
    void createOrderWithInsideOpenTime() {
        CafeKiosk kiosk = new CafeKiosk();
        Beverage americano = new Americano();
        kiosk.add(americano, 1);

        Order order = kiosk.createOrder(LocalDateTime.of(2023, 1, 17, 10, 0, 0));

        assertThat(order.getBeverages().size()).isEqualTo(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("영업시간 외 주문 실행시 예외 발생")
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
