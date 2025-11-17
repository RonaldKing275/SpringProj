package pl.rsz.springproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rsz.springproj.domain.Category;
import pl.rsz.springproj.domain.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String namePhrase, String categoryPhrase);

    List<Product> findByPriceBetween(Float minPrice, Float maxPrice);

    List<Product> findByNameContainingIgnoreCaseAndPriceBetween(
            String namePhrase, Float minPrice, Float maxPrice);

    List<Product> findByCategory(Category category);

    List<Product> findByNameContainingIgnoreCase(String name);
}