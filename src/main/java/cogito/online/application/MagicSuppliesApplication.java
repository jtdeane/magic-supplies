package cogito.online.application;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

@Import(MagicSuppliesConfiguration.class)
public class MagicSuppliesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagicSuppliesApplication.class, args);
    }
}