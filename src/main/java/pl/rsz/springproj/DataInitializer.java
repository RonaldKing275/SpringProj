package pl.rsz.springproj;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.rsz.springproj.domain.*;
import pl.rsz.springproj.repositories.*;

import java.time.LocalDate;
import java.util.Set;

@Configuration
@Log4j2
public class DataInitializer {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TagRepository tagRepository;

    // Nowe repozytoria i encoder
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Bean
    public InitializingBean initDatabase() {
        return () -> {

            // 1. Inicjalizacja Ról
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role("ROLE_USER"));
                roleRepository.save(new Role("ROLE_ADMIN"));
            }

            Role roleUser = roleRepository.findByName("ROLE_USER");
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");

            // 2. Inicjalizacja Użytkowników
            if (userRepository.count() == 0) {
                // Admin
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRoles(Set.of(roleAdmin, roleUser));
                userRepository.save(admin);

                // User
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user"));
                user.setRoles(Set.of(roleUser));
                userRepository.save(user);
            }

            // 3. Twoja stara inicjalizacja Kategorii i Tagów
            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category("Pokarm"));
                categoryRepository.save(new Category("Akcesoria"));
                categoryRepository.save(new Category("Zwierzęta"));
            }

            if (tagRepository.count() == 0) {
                tagRepository.save(new Tag("Dla psa"));
                tagRepository.save(new Tag("Dla kota"));
                tagRepository.save(new Tag("Promocja"));
                tagRepository.save(new Tag("Inne"));
            }

            // Pobieranie do produktów
            Category pokarm = categoryRepository.findAll().get(0);
            Category akcesoria = categoryRepository.findAll().get(1);
            Tag tagPies = tagRepository.findAll().get(0);
            Tag tagKot = tagRepository.findAll().get(1);
            Tag tagPromo = tagRepository.findAll().get(2);
            Tag tagInne = tagRepository.findAll().get(3);

            if (productRepository.count() == 0) {
                Product p1 = new Product();
                p1.setName("Karma dla psa (Wołowina)");
                p1.setCategory(pokarm);
                p1.setPrice(120.50f);
                p1.setBestBeforeDate(LocalDate.of(2026, 10, 20));
                p1.setInStock(true);
                p1.setDimensions(new Dimensions(30f, 50f, 20f));
                p1.setStatus(ProductStatus.AVAILABLE);
                p1.getTags().add(tagPies);
                p1.getTags().add(tagPromo);

                Product p2 = new Product();
                p2.setName("Drapak dla kota");
                p2.setCategory(akcesoria);
                p2.setPrice(250.00f);
                p2.setInStock(true);
                p2.setDimensions(new Dimensions(40f, 120f, 40f));
                p2.setStatus(ProductStatus.AVAILABLE);
                p2.getTags().add(tagKot);

                Product p3 = new Product();
                p3.setName("Żwirek dla kota");
                p3.setCategory(akcesoria);
                p3.setPrice(45.99f);
                p3.setInStock(false);
                p3.setStatus(ProductStatus.UNAVAILABLE);
                p3.getTags().add(tagKot);

                Product p4 = new Product();
                p4.setName("Inny produkty");
                p4.setCategory(akcesoria);
                p4.setPrice(999.00f);
                p4.setInStock(true);
                p4.setDimensions(new Dimensions(12f, 12f, 12f));
                p4.setStatus(ProductStatus.AVAILABLE);
                p4.getTags().add(tagInne);

                productRepository.save(p1);
                productRepository.save(p2);
                productRepository.save(p3);
                productRepository.save(p4);
            }
        };
    }
}