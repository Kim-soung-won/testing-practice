package test.practicetest.unittest;

import lombok.Getter;
import test.practicetest.unittest.beverage.Beverage;
import test.practicetest.unittest.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    public static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
    public static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        this.beverages.add(beverage);
    }

    public void add(Beverage beverage, int count) {
        if(count <= 0) {
            throw new IllegalArgumentException("0잔 이하 주문은 불가능합니다.");
        }
        for(int i = 0; i < count; i++) {
            this.beverages.add(beverage);
        }
    }

    public int calculateTotalPrice() {
        return beverages.stream().map(Beverage::getPrice).reduce(0, Integer::sum);
    }

    public void deleteBeverage(Beverage beverage) {
        this.beverages.remove(beverage);
    }

    public void clear(){
        this.beverages.clear();
    }

    public Order createOrder(LocalDateTime currentDateTime) {
        LocalTime currentTime = currentDateTime.toLocalTime();
        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요");
        }
        return new Order(currentDateTime, beverages);
    }
}
