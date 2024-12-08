package backend.academy.fractal.flame.service.config;

import java.io.InputStream;
import java.io.PrintStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IOConfiguration {

    @Bean
    public PrintStream printStream() {
        return System.out;
    }

    @Bean
    public InputStream inputStream() {
        return System.in;
    }
}
