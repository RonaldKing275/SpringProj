package pl.rsz.springproj.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.rsz.springproj.domain.Category;
import pl.rsz.springproj.domain.Product;
import pl.rsz.springproj.domain.ProductFilter;

import java.time.LocalDate;
import java.util.List;

// ZADANIE 4: Rozszerzenie o JpaSpecificationExecutor
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    // === ZADANIE 1: Zapytanie Nazwane (zdefiniowane w Product.java) ===
    // Szuka frazy w nazwie produktu LUB nazwie kategorii
    // ZADANIE 5: Dodajemy EntityGraph, aby uniknąć LazyInitializationException przy wyłączonym OSIV
    @EntityGraph(attributePaths = {"category", "tags"})
    List<Product> findByNameOrCategoryName(@Param("phrase") String phrase);


    // === ZADANIE 2: @Query modyfikujące ===
    @Modifying
    @Transactional // Wymagane przy operacjach UPDATE/DELETE
    @Query("UPDATE Product p SET p.bestBeforeDate = :newDate WHERE p.id = :id")
    int updateBestBeforeDate(@Param("id") Long id, @Param("newDate") LocalDate newDate);


    // === ZADANIE 3: Wyrażenia SpEL ===
    // Przekazujemy obiekt ProductFilter. SpEL (:#{#filter.xxx}) wyciąga z niego pola.
    // Obsługa opcjonalności: (param IS NULL OR pole = param)
    @EntityGraph(attributePaths = {"category", "tags"}) // Zadanie 5: Eager loading relacji
    @Query("""
        SELECT p FROM Product p 
        WHERE (:#{#filter.phrase} IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :#{#filter.phrase}, '%')))
        AND (:#{#filter.minPrice} IS NULL OR p.price >= :#{#filter.minPrice})
        AND (:#{#filter.maxPrice} IS NULL OR p.price <= :#{#filter.maxPrice})
        AND (:#{#filter.categoryId} IS NULL OR p.category.id = :#{#filter.categoryId})
    """)
    List<Product> findWithFilter(@Param("filter") ProductFilter filter);


    // Nadpisanie findAll, aby dodać EntityGraph (dla Zadania 5 - rozwiązanie problemu N+1)
    @Override
    @EntityGraph(attributePaths = {"category", "tags"})
    List<Product> findAll();
}