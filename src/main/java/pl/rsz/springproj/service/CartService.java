package pl.rsz.springproj.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import pl.rsz.springproj.domain.OrderItem;
import pl.rsz.springproj.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {
    private List<OrderItem> items = new ArrayList<>();

    public void addToCart(Product product) {
        // Sprawdź czy już jest, jak tak to zwiększ ilość
        Optional<OrderItem> existing = items.stream().filter(i -> i.getProduct().getId().equals(product.getId())).findFirst();
        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + 1);
        } else {
            items.add(new OrderItem(product, 1));
        }
    }

    public List<OrderItem> getItems() { return items; }
    public void clear() { items.clear(); }
    public float getTotal() { return (float) items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum(); }
}