package pl.rsz.springproj.domain;

import jakarta.validation.constraints.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{NotBlank.product.name}")
    @Size(min = 3, max = 50, message = "{Size.product.name}")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "{NotBlank.product.category}")
    private String category;

    @NumberFormat(pattern = "#.00")
    @NotNull(message = "{NotNull.product.price}")
    @Min(value = 0, message = "{Min.product.price}")
    private Float price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "{Future.product.bestBeforeDate}")
    private LocalDate bestBeforeDate;

    private boolean inStock;

    @Embedded
    private Dimensions dimensions;

    private String status;

    public Product() {
        this.dimensions = new Dimensions();
    }

    public Product(Long id, String name, String category, Float price, LocalDate bestBeforeDate, boolean inStock) {
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}