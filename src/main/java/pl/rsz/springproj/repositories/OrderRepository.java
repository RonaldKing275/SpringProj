package pl.rsz.springproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rsz.springproj.domain.Order;
import pl.rsz.springproj.domain.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderDateDesc(User user);
}