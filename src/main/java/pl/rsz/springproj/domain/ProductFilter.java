package pl.rsz.springproj.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilter {
    private String phrase;
    private Float minPrice;
    private Float maxPrice;
    private Long categoryId;
}