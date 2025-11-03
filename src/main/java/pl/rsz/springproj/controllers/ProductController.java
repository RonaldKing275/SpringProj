package pl.rsz.springproj.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.rsz.springproj.domain.Product;
import pl.rsz.springproj.repositories.ProductRepository;
import pl.rsz.springproj.validators.ProductValidator;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new ProductValidator());
    }

    @GetMapping("/product-list")
    public String showProductList(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "product-list-view";
    }

    @GetMapping("/product-details")
    public String showProductDetails(@RequestParam("id") Long productId, Model model) {
        Product product = productRepository.findById(productId)
                .orElse(null);

        if (product != null) {
            model.addAttribute("product", product);
            return "product-details";
        } else {
            return "redirect:/product-list";
        }
    }

    @GetMapping("/product-delete")
    public String deleteProduct(@RequestParam("id") Long productId) {
        productRepository.deleteById(productId);
        return "redirect:/product-list";
    }

    @GetMapping(path = {"/product/add", "/product/edit/{id}"})
    public String showForm(
            @PathVariable(name = "id", required = false) Long productId,
            Model model) {

        Product product;
        if (productId != null) {
            product = productRepository.findById(productId)
                    .orElse(new Product());
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

        if (result.hasErrors()) {
            return "product-form";
        }

        productRepository.save(product);

        return "redirect:/product-list";
    }
}