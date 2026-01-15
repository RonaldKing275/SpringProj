package pl.rsz.springproj.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // Ważne!
import pl.rsz.springproj.domain.*;
import pl.rsz.springproj.repositories.CategoryRepository;
import pl.rsz.springproj.repositories.TagRepository;
import pl.rsz.springproj.service.ProductService; // Używamy serwisu
import pl.rsz.springproj.validators.ProductValidator;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService; // Zamiast Repository
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Autowired
    public ProductController(ProductService productService, CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new ProductValidator());
    }

    @GetMapping("/product-list")
    public String showProductList(ProductFilter filter, Model model) { // Uproszczone pobieranie parametrów
        List<Product> products = productService.getProductsWithFilter(filter);
        model.addAttribute("products", products);
        return "product-list-view";
    }

    @GetMapping("/product-details")
    public String showProductDetails(@RequestParam("id") Long productId, Model model) {
        Product product = productService.getProductById(productId); // Serwis rzuci wyjątek jak nie znajdzie
        model.addAttribute("product", product);
        return "product-details";
    }

    @GetMapping("/product-delete")
    public String deleteProduct(@RequestParam("id") Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/product-list";
    }

    @GetMapping(path = {"/product/add", "/product/edit/{id}"})
    public String showForm(@PathVariable(name = "id", required = false) Long productId, Model model) {
        Product product = (productId != null) ? productService.getProductById(productId) : new Product();
        model.addAttribute("product", product);
        return "product-form";
    }

    // === LAB 10: Obsługa uploadu pliku ===
    @PostMapping("/product/save")
    public String processForm(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            @RequestParam("imageFile") MultipartFile imageFile // Dodatkowy parametr
    ) {
        if (result.hasErrors()) {
            return "product-form";
        }
        productService.saveProduct(product, imageFile);
        return "redirect:/product-list";
    }

    // ... (ModelAttributes bez zmian)
    @ModelAttribute("categories")
    public List<Category> loadCategories() { return categoryRepository.findAll(); }
    @ModelAttribute("statuses")
    public ProductStatus[] loadStatuses() { return ProductStatus.values(); }
    @ModelAttribute("tags")
    public List<Tag> loadTags() { return tagRepository.findAll(); }
}