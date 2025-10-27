package pl.rsz.springproj.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Product implements Serializable {

    private Long id;
    private String name;
    private String category;
    private Float price;
    private LocalDate bestBeforeDate;
    private boolean inStock;

    public Product() {
    }

    public Product(Long id, String name, String category, Float price, LocalDate bestBeforeDate, boolean inStock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.bestBeforeDate = bestBeforeDate;
        this.inStock = inStock;
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
}