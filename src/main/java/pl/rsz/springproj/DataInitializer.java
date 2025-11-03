package pl.rsz.springproj;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.rsz.springproj.domain.Category;
import pl.rsz.springproj.domain.Dimensions;
import pl.rsz.springproj.domain.Product;
import pl.rsz.springproj.domain.ProductStatus;
import pl.rsz.springproj.repositories.CategoryRepository;
import pl.rsz.springproj.repositories.ProductRepository;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Bean
    public InitializingBean initDatabase() {
        return () -> {

            if (categoryRepository.count() == 0) {
                Category c1 = new Category("Pokarm");
                Category c2 = new Category("Akcesoria");
                Category c3 = new Category("Zwierzęta");

                categoryRepository.save(c1);
                categoryRepository.save(c2);
                categoryRepository.save(c3);
            }

            if (productRepository.count() == 0) {

                Category pokarm = categoryRepository.findAll().get(0);
                Category akcesoria = categoryRepository.findAll().get(1);

                Product p1 = new Product();
                p1.setName("Karma dla psa (Wołowina)");
                p1.setCategory(pokarm);
                p1.setPrice(120.50f);
                p1.setBestBeforeDate(LocalDate.of(2026, 10, 20));
                p1.setInStock(true);
                p1.setDimensions(new Dimensions(30f, 50f, 20f));
                p1.setStatus(ProductStatus.AVAILABLE);

                Product p2 = new Product();
                p2.setName("Drapak dla kota");
                p2.setCategory(akcesoria);
                p2.setPrice(250.00f);
                p2.setInStock(true);
                p2.setDimensions(new Dimensions(40f, 120f, 40f));
                p2.setStatus(ProductStatus.AVAILABLE);

                Product p3 = new Product();
                p3.setName("Żwirek dla kota");
                p3.setCategory(akcesoria);
                p3.setPrice(45.99f);
                p3.setInStock(false);
                p3.setStatus(ProductStatus.UNAVAILABLE);

                Product p4 = new Product();
                p4.setName("Szczotka do sierści");
                p4.setCategory(akcesoria);
                p4.setPrice(20.50f);
                p4.setBestBeforeDate(LocalDate.of(2099, 12, 12));
                p4.setInStock(true);
                p4.setDimensions(new Dimensions(10f, 15f, 4.5f));
                p4.setStatus(ProductStatus.AVAILABLE);

                Product p5 = new Product();
                p5.setName("Kocimiętka");
                p5.setCategory(akcesoria);
                p5.setPrice(999.99f);
                p5.setBestBeforeDate(LocalDate.of(2029, 8, 31));
                p5.setInStock(false);
                p5.setDimensions(new Dimensions(5f, 5f, 5f));
                p5.setStatus(ProductStatus.ON_ORDER);

                productRepository.save(p1);
                productRepository.save(p2);
                productRepository.save(p3);
                productRepository.save(p4);
                productRepository.save(p5);
            }
        };
    }
}