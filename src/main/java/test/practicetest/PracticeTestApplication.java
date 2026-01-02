package test.practicetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PracticeTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeTestApplication.class, args);
    }

}
