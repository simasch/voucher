package ch.martinelli.demo.windsurf.voucher;

import org.springframework.boot.SpringApplication;

public class TestWindsurfDemoApplication {

    public static void main(String[] args) {
        SpringApplication.from(WindsurfDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
