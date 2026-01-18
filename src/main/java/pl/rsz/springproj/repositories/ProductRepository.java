package pl.rsz.springproj.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.rsz.springproj.domain.Product;
import pl.rsz.springproj.domain.ProductFilter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @EntityGraph(attributePaths = {"category", "tags"})
    Optional<Product> findById(Long id);

    @EntityGraph(attributePaths = {"category", "tags"})
    List<Product> findByNameOrCategoryName(@Param("phrase") String phrase);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.bestBeforeDate = :newDate WHERE p.id = :id")
    int updateBestBeforeDate(@Param("id") Long id, @Param("newDate") LocalDate newDate);

    @EntityGraph(attributePaths = {"category", "tags"})
    @Query("""
        SELECT p FROM Product p 
        WHERE (:#{#filter.phrase} IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :#{#filter.phrase}, '%')))
        AND (:#{#filter.minPrice} IS NULL OR p.price >= :#{#filter.minPrice})
        AND (:#{#filter.maxPrice} IS NULL OR p.price <= :#{#filter.maxPrice})
        AND (:#{#filter.categoryId} IS NULL OR p.category.id = :#{#filter.categoryId})
    """)
    List<Product> findWithFilter(@Param("filter") ProductFilter filter);

    @Override
    @EntityGraph(attributePaths = {"category", "tags"})
    List<Product> findAll();
}