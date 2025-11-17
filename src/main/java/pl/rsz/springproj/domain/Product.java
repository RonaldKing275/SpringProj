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

import java.util.Set;
import java.util.HashSet;

import pl.rsz.springproj.domain.Tag;
import pl.rsz.springproj.repositories.TagRepository;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{NotBlank.product.name}")
    @Size(min = 3, max = 50, message = "{Size.product.name}")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Musisz wybrać kategorię")
    @ManyToOne
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

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags = new HashSet<>();

//    @Embedded
//    private TagRepository tagi;
}