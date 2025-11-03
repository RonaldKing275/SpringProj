package pl.rsz.springproj.domain;

public enum ProductStatus {
    AVAILABLE("Dostępny"),
    UNAVAILABLE("Niedostępny"),
    ON_ORDER("Na zamówienie");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}