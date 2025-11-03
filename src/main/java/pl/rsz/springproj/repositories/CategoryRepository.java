package pl.rsz.springproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rsz.springproj.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}