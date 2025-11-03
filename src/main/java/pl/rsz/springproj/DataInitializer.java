package pl.rsz.springproj;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.rsz.springproj.domain.Category; // <-- NOWY IMPORT
import pl.rsz.springproj.domain.Dimensions;
import pl.rsz.springproj.domain.Product;
import pl.rsz.springproj.domain.ProductStatus; // <-- NOWY IMPORT
import pl.rsz.springproj.repositories.CategoryRepository; // <-- NOWY IMPORT
import pl.rsz.springproj.repositories.ProductRepository;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Autowired
    private ProductRepository productRepository;

    // (Zadanie 4) Wstrzyknięcie CategoryRepository (może być przez pole)
    @Autowired
    private CategoryRepository categoryRepository;

    @Bean
    public InitializingBean initDatabase() {
        return () -> {

            // (Zadanie 4) Najpierw tworzymy i zapisujemy kategorie
            if (categoryRepository.count() == 0) {
                Category c1 = new Category("Pokarm");
                Category c2 = new Category("Akcesoria");
                Category c3 = new Category("Zwierzęta");

                categoryRepository.save(c1);
                categoryRepository.save(c2);
                categoryRepository.save(c3);
            }

            // (Zadanie 3) Tworzymy produkty, tylko jeśli baza jest pusta
            if (productRepository.count() == 0) {

                // Pobieramy zapisane kategorie, aby ich użyć
                Category pokarm = categoryRepository.findAll().get(0);
                Category akcesoria = categoryRepository.findAll().get(1);

                // Tworzymy produkty z poprawnymi relacjami i enumami
                Product p1 = new Product();
                p1.setName("Karma dla psa (Wołowina)");
                p1.setCategory(pokarm); // <-- POPRAWKA
                p1.setPrice(120.50f);
                p1.setBestBeforeDate(LocalDate.of(2026, 10, 20));
                p1.setInStock(true);
                p1.setDimensions(new Dimensions(30f, 50f, 20f));
                p1.setStatus(ProductStatus.AVAILABLE); // <-- POPRAWKA

                Product p2 = new Product();
                p2.setName("Drapak dla kota");
                p2.setCategory(akcesoria); // <-- POPRAWKA
                p2.setPrice(250.00f);
                p2.setInStock(true);
                p2.setDimensions(new Dimensions(40f, 120f, 40f));
                p2.setStatus(ProductStatus.AVAILABLE); // <-- POPRAWKA

                Product p3 = new Product();
                p3.setName("Żwirek dla kota");
                p3.setCategory(akcesoria); // <-- POPRAWKA
                p3.setPrice(45.99f);
                p3.setInStock(false);
                p3.setStatus(ProductStatus.UNAVAILABLE); // <-- POPRAWKA

                productRepository.save(p1);
                productRepository.save(p2);
                productRepository.save(p3);
            }
        };
    }
}