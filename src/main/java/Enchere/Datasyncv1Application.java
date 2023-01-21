package Enchere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "Enchere.controllerVIEW")
public class Datasyncv1Application {

    public static void main(String[] args) {
        SpringApplication.run(Datasyncv1Application.class, args);
    }
}
