package pl.rsz.springproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rsz.springproj.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}