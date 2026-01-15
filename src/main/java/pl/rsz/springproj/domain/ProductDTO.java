package pl.rsz.springproj.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore; // Jeśli potrzebne

import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private Float price;
    private String categoryName; // Tylko nazwa kategorii w API
    private Long categoryId; // Do zapisu

    // === LAB 11: Metody konwersji W DTO ===

    // Z Encji do DTO
    public static ProductDTO fromEntity(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
            dto.setCategoryId(product.getCategory().getId());
        }
        return dto;
    }

    // Z DTO do Encji (pomocnicza, uproszczona)
    public Product toEntity() {
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.name);
        product.setPrice(this.price);
        // Uwaga: Kategorię i tagi trzeba by pobrać z bazy w serwisie,
        // DTO przekazuje tylko proste dane.
        return product;
    }
}