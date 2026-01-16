package pl.rsz.springproj.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    private String status; // np. "NEW", "COMPLETED"

    @CreatedDate
    private LocalDateTime orderDate;

    private float totalPrice;

    public void calculateTotal() {
        this.totalPrice = (float) items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
    }
}