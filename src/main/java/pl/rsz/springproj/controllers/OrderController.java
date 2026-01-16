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

    // Ten endpoint wyświetla formularz zamówienia (checkout.html)
    @GetMapping("/order/checkout")
    public String checkout(Model model) {
        // Jeśli koszyk jest pusty, przekieruj do sklepu
        if (cartService.getItems().isEmpty()) {
            return "redirect:/product-list";
        }

        // Przekazujemy pusty obiekt adresu do formularza i sumę do zapłaty
        model.addAttribute("address", new Address());
        model.addAttribute("total", cartService.getTotal());

        return "checkout"; // Szuka pliku checkout.html w templates
    }

    // Ten endpoint odbiera dane z formularza i składa zamówienie
    @PostMapping("/order/submit")
    public String submitOrder(Address address, @AuthenticationPrincipal UserDetails userDetails) {
        // Pobieramy aktualnie zalogowanego użytkownika z bazy
        User user = userRepository.findByUsername(userDetails.getUsername());

        // Zlecamy serwisowi stworzenie zamówienia
        orderService.placeOrder(user, address, cartService.getItems());

        // Czyścimy koszyk po udanym zakupie
        cartService.clear();

        // Przekierowujemy do panelu klienta, żeby zobaczył swoje zamówienie
        return "redirect:/panel";
    }

    // Ten endpoint wyświetla historię zamówień (client-panel.html)
    @GetMapping("/panel")
    public String clientPanel(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("orders", orderService.getUserOrders(userDetails.getUsername()));
        return "client-panel";
    }
}