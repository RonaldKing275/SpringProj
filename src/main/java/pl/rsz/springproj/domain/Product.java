package pl.rsz.springproj.domain;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.time.LocalDate;

public class Product implements Serializable {

    private Long id;

    @NotBlank(message = "{NotBlank.product.name}")
    @Size(min = 3, max = 50, message = "{Size.product.name}")
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

    private Dimensions dimensions;

    public Product() {
        this.dimensions = new Dimensions();
    }

    public Product(Long id, String name, String category, Float price, LocalDate bestBeforeDate, boolean inStock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.bestBeforeDate = bestBeforeDate;
        this.inStock = inStock;

        this.dimensions = new Dimensions();
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
}