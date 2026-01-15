package pl.rsz.springproj.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rsz.springproj.domain.Product;
import pl.rsz.springproj.domain.ProductDTO;
import pl.rsz.springproj.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    // GET - Lista wszystkich (jako DTO)
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> dtos = productService.getAllProducts().stream()
                .map(ProductDTO::fromEntity) // Użycie metody konwertującej z DTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET - Pojedynczy produkt
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(ProductDTO.fromEntity(product));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Możesz dodać POST, PUT, DELETE analogicznie...
}