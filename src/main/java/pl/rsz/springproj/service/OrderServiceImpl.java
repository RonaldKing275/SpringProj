package pl.rsz.springproj.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.rsz.springproj.domain.Address;
import pl.rsz.springproj.domain.Order;
import pl.rsz.springproj.domain.OrderItem;
import pl.rsz.springproj.domain.User;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl {
    // ... wstrzykiwanie repozytoriów ...

    public void placeOrder(User user, Address address, List<OrderItem> cartItems) {
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setStatus("NEW");
        order.setItems(new ArrayList<>(cartItems)); // Kopiujemy itemy
        order.calculateTotal();

        orderRepository.save(order);
    }

    public List<Order> getUserOrders(String username) {
        User user = userRepository.findByUsername(username);
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }
}