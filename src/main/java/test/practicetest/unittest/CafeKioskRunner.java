package test.practicetest.unittest;

import test.practicetest.unittest.beverage.Americano;
import test.practicetest.unittest.beverage.Latte;

public class CafeKioskRunner {

    public static void main(String[] args) {
        CafeKiosk kiosk = new CafeKiosk();
        kiosk.add( new Americano());
        System.out.println(">>>>> 아메리카노 추가");

        kiosk.add( new Latte());
        System.out.println(">>>>> 라떼 추가");

        int totalPrice = kiosk.calculateTotalPrice();
        System.out.println("총 주문 금액: " + totalPrice + "원");
    }
}
