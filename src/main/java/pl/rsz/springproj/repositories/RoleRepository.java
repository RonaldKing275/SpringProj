package pl.rsz.springproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rsz.springproj.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}