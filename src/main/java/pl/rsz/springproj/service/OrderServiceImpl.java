package pl.rsz.springproj.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.rsz.springproj.domain.Address;
import pl.rsz.springproj.domain.Order;
import pl.rsz.springproj.domain.OrderItem;
import pl.rsz.springproj.domain.User;
import pl.rsz.springproj.repositories.OrderRepository;
import pl.rsz.springproj.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl {

    // === 1. Deklaracja pól repozytoriów ===
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    // === 2. Wstrzykiwanie przez konstruktor ===
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // === 3. Metody biznesowe ===

    public void placeOrder(User user, Address address, List<OrderItem> cartItems) {
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setStatus("NEW");

        // Tworzymy nową listę pozycji, aby nie modyfikować koszyka w sesji bezpośrednio
        order.setItems(new ArrayList<>(cartItems));

        order.calculateTotal();

        orderRepository.save(order);
    }

    public List<Order> getUserOrders(String username) {
        // Teraz zmienna userRepository jest widoczna dzięki deklaracji na górze
        User user = userRepository.findByUsername(username);

        // Teraz zmienna orderRepository jest widoczna
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }
}