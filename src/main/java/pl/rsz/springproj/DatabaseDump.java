package pl.rsz.springproj;

import org.springframework.cglib.core.Local;
import pl.rsz.springproj.domain.Dimensions;
import pl.rsz.springproj.domain.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDump {

    private static List<Product> productList = new ArrayList<>();

    static {
        long id = 0;
        Product product = new Product(
                id++, "Karma dla psa (Wołowina)", "Pokarm", 120.50f, 
                LocalDate.of(2026, 10, 20), true);
        product.setDimensions(new Dimensions(30F, 75.2F, 15.5F));
        productList.add(product);
        
        product = new Product(id++, "Drapak dla kota", "Akcesoria", 250.00f, 
                LocalDate.of(2045, 12, 12), true);
        product.setDimensions(new Dimensions(55.5F, 150F, 55.5F));
        productList.add(product);
        
        product = new Product(id++, "Żwirek dla kota", "Akcesoria", 45.99f, 
                LocalDate.of(2030, 12, 12), false);
        product.setDimensions(new Dimensions(15.2F, 15.2F, 4.2F));
        productList.add(product);
        
        product = new Product(id++, "Chomik Syryjski", "Zwierzęta", 30.00f,
                LocalDate.of(2026, 12, 15), true);
        product.setDimensions(new Dimensions(4.4F, 4.3F, 11.5F));
        productList.add(product);
    }

    public static List<Product> getAllProducts() {
        return productList;
    }

    public static Product findProductById(Long id, Product defaultVal) {
        var product = productList.stream().filter(x -> x.getId().equals(id)).findFirst();
        return product.orElse(defaultVal);
    }

    public static void deleteProductById(Long id) {
        productList.removeIf(product -> product.getId().equals(id));
    }

    public static void saveOrUpdateProduct(Product product) {
        if (product.getId() == null) {
            long newId = productList.stream()
                    .mapToLong(Product::getId)
                    .max()
                    .orElse(0L) + 1;
            product.setId(newId);
            productList.add(product);
        } else {
            var productToUpdate = findProductById(product.getId(), null);
            if (productToUpdate != null) {
                var idx = productList.indexOf(productToUpdate);
                if (idx != -1) {
                    productList.set(idx, product);
                }
            }
        }
    }
}