package backend.academy;

import backend.academy.fractal.flame.service.app.Application;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "backend.academy.fractal.flame")
public class Main {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
    }
}
