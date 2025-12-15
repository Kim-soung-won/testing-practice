package test.practicetest.unittest;

import lombok.Getter;
import test.practicetest.unittest.beverage.Beverage;
import test.practicetest.unittest.order.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        this.beverages.add(beverage);
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

    public Order createOrder() {
        return new Order(LocalDateTime.now(), beverages);
    }
}
