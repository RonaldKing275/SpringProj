package pl.rsz.springproj.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.rsz.springproj.domain.Address;
import pl.rsz.springproj.domain.User;
import pl.rsz.springproj.repositories.UserRepository;
import pl.rsz.springproj.service.CartService;
import pl.rsz.springproj.service.OrderServiceImpl;

@Controller
public class OrderController {

    private final CartService cartService;
    private final OrderServiceImpl orderService;
    private final UserRepository userRepository;

    public OrderController(CartService cartService, OrderServiceImpl orderService, UserRepository userRepository) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @GetMapping("/order/checkout")
    public String checkout(Model model) {
        if (cartService.getItems().isEmpty()) {
            return "redirect:/product-list";
        }

        model.addAttribute("address", new Address());
        model.addAttribute("total", cartService.getTotal());

        return "checkout";
    }

    @PostMapping("/order/submit")
    public String submitOrder(Address address, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());

        orderService.placeOrder(user, address, cartService.getItems());

        cartService.clear();

        return "redirect:/panel";
    }

    @GetMapping("/panel")
    public String clientPanel(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("orders", orderService.getUserOrders(userDetails.getUsername()));
        return "client-panel";
    }
}