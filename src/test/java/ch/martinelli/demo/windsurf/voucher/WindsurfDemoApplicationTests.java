package ch.martinelli.demo.windsurf.voucher;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class WindsurfDemoApplicationTests {

    @Test
    void contextLoads() {
    }

}
