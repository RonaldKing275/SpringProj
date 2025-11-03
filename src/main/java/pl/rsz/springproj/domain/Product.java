package pl.rsz.springproj.domain;

// Importy dla JPA (Lab 5)
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;

// Importy dla walidacji (Lab 4)
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Importy dla formatowania (Lab 4)
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Główna klasa encji reprezentująca Produkt.
 * Łączy wymagania z Lab 2 (Java Bean), Lab 4 (Walidacja, Formatowanie)
 * i Lab 5 (Encja JPA, Relacje).
 */
@Entity // Mówi JPA, że ta klasa jest tabelą w bazie danych
public class Product implements Serializable { // Wymaganie z Lab 2

    // --- Pola Encji ---

    @Id // Oznacza klucz główny
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatyczna inkrementacja ID
    private Long id;

    @NotBlank(message = "{NotBlank.product.name}") // Walidacja z Lab 4
    @Size(min = 3, max = 50, message = "{Size.product.name}") // Walidacja z Lab 4
    @Column(nullable = false) // Kolumna nie może być pusta (JPA, Lab 5)
    private String name;

    @NotNull(message = "Musisz wybrać kategorię") // Walidacja dla obiektu
    @ManyToOne // Relacja Wiele-do-Jednego (Lab 5)
    private Category category; // Zmiana z String na Category (Lab 5)

    @NumberFormat(pattern = "#.00") // Formatowanie z Lab 4
    @NotNull(message = "{NotNull.product.price}") // Walidacja z Lab 4
    @Min(value = 0, message = "{Min.product.price}") // Walidacja z Lab 4
    private Float price;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // Formatowanie z Lab 4
    @Future(message = "{Future.product.bestBeforeDate}") // Walidacja z Lab 4
    private LocalDate bestBeforeDate;

    private boolean inStock;

    @Embedded // Mówi JPA, aby wbudowało tu pola z klasy Dimensions (Lab 5)
    private Dimensions dimensions;

    @NotNull(message = "Musisz wybrać status") // Walidacja dla obiektu
    @Enumerated(EnumType.STRING) // Zapisuje ENUM jako tekst w bazie (Lab 5)
    private ProductStatus status; // Zmiana z String na ProductStatus (Lab 5)

    // --- Konstruktory ---

    /**
     * Konstruktor bezargumentowy wymagany przez JPA i Java Beans.
     */
    public Product() {
        // Inicjalizacja wbudowanego obiektu (z Lab 4)
        this.dimensions = new Dimensions();
    }

    // --- Gettery i Settery (wymagane przez Java Beans) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public LocalDate getBestBeforeDate() {
        return bestBeforeDate;
    }

    public void setBestBeforeDate(LocalDate bestBeforeDate) {
        this.bestBeforeDate = bestBeforeDate;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}