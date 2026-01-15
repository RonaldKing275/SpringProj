package pl.rsz.springproj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.rsz.springproj.domain.Product;
import pl.rsz.springproj.domain.ProductFilter;
import pl.rsz.springproj.exceptions.ProductNotFoundException;
import pl.rsz.springproj.repositories.ProductRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional // Lab 9: Zarządzanie transakcjami
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // Lab 10: Ścieżka do zapisu plików (można przenieść do application.properties)
    private final String UPLOAD_DIR = "uploads/products/";

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsWithFilter(ProductFilter filter) {
        return productRepository.findWithFilter(filter);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id)); // Lab 9: Rzucanie wyjątku
    }

    @Override
    public void saveProduct(Product product, MultipartFile imageFile) {
        // Lab 10: Obsługa uploadu pliku
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = imageFile.getOriginalFilename();
                // Zapisujemy samą nazwę pliku w bazie
                product.setImagePath(fileName);

                // Zapis fizyczny (najpierw zapisz produkt by mieć ID, jeśli to nowe)
                Product saved = productRepository.save(product);

                Path uploadPath = Paths.get(UPLOAD_DIR + saved.getId());
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                throw new RuntimeException("Błąd zapisu pliku", e);
            }
        } else {
            productRepository.save(product);
        }
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public void updateBestBeforeDate(Long id) {
        productRepository.updateBestBeforeDate(id, LocalDate.now().plusYears(1));
    }
}