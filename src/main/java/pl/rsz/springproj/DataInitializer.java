package pl.rsz.springproj;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.rsz.springproj.domain.Dimensions;
import pl.rsz.springproj.domain.Product;
import pl.rsz.springproj.repositories.ProductRepository;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Autowired
    private ProductRepository productRepository;

    @Bean
    public InitializingBean initDatabase() {
        return () -> {
            if (productRepository.count() == 0) {
                Product p1 = new Product();
                p1.setName("Karma dla psa (Wołowina)");
                p1.setCategory("Pokarm");
                p1.setPrice(120.50f);
                p1.setBestBeforeDate(LocalDate.of(2026, 10, 20));
                p1.setInStock(true);
                p1.setDimensions(new Dimensions(30f, 50f, 20f));
                p1.setStatus("Dostępny");

                Product p2 = new Product();
                p2.setName("Drapak dla kota");
                p2.setCategory("Akcesoria");
                p2.setPrice(250.00f);
                p2.setInStock(true);
                p2.setDimensions(new Dimensions(40f, 120f, 40f));
                p2.setStatus("Dostępny");

                Product p3 = new Product();
                p3.setName("Żwirek dla kota");
                p3.setCategory("Akcesoria");
                p3.setPrice(45.99f);
                p3.setInStock(false);
                p3.setStatus("Brak");

                productRepository.save(p1);
                productRepository.save(p2);
                productRepository.save(p3);
            }
        };
    }
}