package pl.rsz.springproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rsz.springproj.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}