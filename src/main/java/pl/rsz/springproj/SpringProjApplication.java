package pl.rsz.springproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.format.Formatter;
import pl.rsz.springproj.domain.Dimensions;
import pl.rsz.springproj.formatters.DimensionsFormatter;

@SpringBootApplication
public class SpringProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProjApplication.class, args);
    }

    @Bean
    public Formatter<Dimensions> dimensionsFormatter() {
        return new DimensionsFormatter();
    }
}
