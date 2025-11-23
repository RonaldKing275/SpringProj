package pl.rsz.springproj.controllers;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.rsz.springproj.domain.*;
import pl.rsz.springproj.repositories.CategoryRepository;
import pl.rsz.springproj.repositories.ProductRepository;
import pl.rsz.springproj.repositories.TagRepository;
import pl.rsz.springproj.validators.ProductValidator;

import java.time.LocalDate;
import java.util.List;

@Controller
@Log4j2
public class ProductController {

    private final ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, TagRepository tagRepository) {
        this.productRepository = productRepository;
        this.tagRepository = tagRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new ProductValidator());
    }

    @GetMapping("/product-list")
    public String showProductList(
            @RequestParam(name = "phrase", required = false) String phrase,
            @RequestParam(name = "minPrice", required = false) Float minPrice,
            @RequestParam(name = "maxPrice", required = false) Float maxPrice,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            Model model
    ) {
        log.info("Wyświetlanie listy produktów z filtrami");
        List<Product> products;
        ProductFilter filter = new ProductFilter(phrase, minPrice, maxPrice, categoryId);
        products = productRepository.findWithFilter(filter);

        if (phrase != null && !phrase.isEmpty()) {
            log.info("Test NamedQuery: Znaleziono " + productRepository.findByNameOrCategoryName(phrase).size() + " wyników.");
        }

        model.addAttribute("products", products);
        return "product-list-view";
    }

    @GetMapping("/product-details")
    public String showProductDetails(@RequestParam("id") Long productId, Model model) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.getTags().size();
            model.addAttribute("product", product);
            return "product-details";
        } else {
            return "redirect:/product-list";
        }
    }

    @GetMapping("/product/update-date")
    @ResponseBody
    public String updateDate(@RequestParam Long id) {
        int updatedRows = productRepository.updateBestBeforeDate(id, LocalDate.now().plusYears(1));
        return "Zaktualizowano datę dla " + updatedRows + " produktów.";
    }

    @GetMapping("/product-delete")
    public String deleteProduct(@RequestParam("id") Long productId) {
        log.info("Usuwanie produktu o id: " + productId);
        productRepository.deleteById(productId);
        return "redirect:/product-list";
    }

    @GetMapping(path = {"/product/add", "/product/edit/{id}"})
    public String showForm(
            @PathVariable(name = "id", required = false) Long productId,
            Model model) {
        Product product;
        if (productId != null) {
            product = productRepository.findById(productId).orElse(new Product());
        } else {
            product = new Product();
        }
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping("/product/save")
    public String processForm(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result
    ) {
        log.info("Próba zapisu produktu: " + product.getName());

        if (result.hasErrors()) {
            log.warn("Błędy walidacji podczas zapisu!");
            return "product-form";
        }
        productRepository.save(product);
        return "redirect:/product-list";
    }

    @ModelAttribute("categories")
    public List<Category> loadCategories() {
        return categoryRepository.findAll();
    }

    @ModelAttribute("statuses")
    public ProductStatus[] loadStatuses() {
        return ProductStatus.values();
    }

    @ModelAttribute("tags")
    public List<Tag> loadTags() {
        return tagRepository.findAll();
    }
}