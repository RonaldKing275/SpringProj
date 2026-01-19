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

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public void placeOrder(User user, Address address, List<OrderItem> cartItems) {
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setStatus("NEW");

        order.setItems(new ArrayList<>(cartItems));

        order.calculateTotal();

        orderRepository.save(order);
    }

    public List<Order> getUserOrders(String username) {
        User user = userRepository.findByUsername(username);
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }
}