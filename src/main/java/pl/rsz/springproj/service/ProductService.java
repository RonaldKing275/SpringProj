package pl.rsz.springproj.service;

import org.springframework.web.multipart.MultipartFile;
import pl.rsz.springproj.domain.Product;
import pl.rsz.springproj.domain.ProductFilter;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    List<Product> getProductsWithFilter(ProductFilter filter);
    Product getProductById(Long id);
    void saveProduct(Product product, MultipartFile imageFile); // Dodajemy od razu obsługę pliku z Lab 10
    void deleteProduct(Long id);
    void updateBestBeforeDate(Long id);
}