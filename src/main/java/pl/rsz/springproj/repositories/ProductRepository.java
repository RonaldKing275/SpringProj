package pl.rsz.springproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rsz.springproj.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}