package test.practicetest.unittest.beverage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {

    @Test
    void getName() {
        Americano americano = new Americano();
        // JUnit5의 API
        assertEquals("아메리카노", americano.getName());

        // AssertJ의 API, JUnit5 보다 간결하게 사용할 수 있다.
        assertThat(americano.getName()).isEqualTo("아메리카노");
    }

    @Test
    void getPrice() {
        Americano americano = new Americano();
        assertThat(americano.getPrice()).isEqualTo(3000);
    }
}