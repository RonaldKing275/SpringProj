package pl.rsz.springproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rsz.springproj.domain.Category;
import pl.rsz.springproj.domain.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1. Wyszukiwanie frazy w nazwie (ignoruje wielkość liter)
    List<Product> findByNameContainingIgnoreCase(String name);

    // 2. Wyszukiwanie po zakresie cen
    List<Product> findByPriceBetween(Float minPrice, Float maxPrice);

    // 3. Wyszukiwanie po zawartości relacji Many-To-One
    List<Product> findByCategory(Category category);

    // 4. Wyszukiwanie frazy w nazwie LUB nazwie kategorii (ignoruje wielkość liter)
    List<Product> findByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String namePhrase, String categoryPhrase);
}