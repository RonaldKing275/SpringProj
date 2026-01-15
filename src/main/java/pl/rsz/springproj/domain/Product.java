package pl.rsz.springproj.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NamedQuery(
        name = "Product.findByNameOrCategoryName",
        query = "SELECT p FROM Product p LEFT JOIN p.category c WHERE " +
                "LOWER(p.name) LIKE LOWER(CONCAT('%', :phrase, '%')) OR " +
                "LOWER(c.name) LIKE LOWER(CONCAT('%', :phrase, '%'))"
)
@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{NotBlank.product.name}")
    @Size(min = 3, max = 50, message = "{Size.product.name}")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Musisz wybrać kategorię")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @NumberFormat(pattern = "#.00")
    @NotNull(message = "{NotNull.product.price}")
    @Min(value = 0, message = "{Min.product.price}")
    private Float price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "{Future.product.bestBeforeDate}")
    private LocalDate bestBeforeDate;

    private boolean inStock;

    @Embedded
    private Dimensions dimensions = new Dimensions();

    @NotNull(message = "Musisz wybrać status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Tag> tags = new HashSet<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    private String imagePath;

    public Set<Tag> getTags() {
        return tags;
    }
}