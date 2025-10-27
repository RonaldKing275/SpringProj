package pl.rsz.springproj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.rsz.springproj.DatabaseDump;
import pl.rsz.springproj.domain.Product;

@Controller
public class ProductController {

    @GetMapping("/product-list")
    public String showProductList(Model model) {
        model.addAttribute("products", DatabaseDump.getAllProducts());
        return "product-list-view";
    }

    @GetMapping("/product-details")
    public String showProductDetails(@RequestParam("id") Long productId, Model model) {
        Product product = DatabaseDump.findProductById(productId, null);

        if (product != null) {
            model.addAttribute("product", product);
            return "product-details";
        } else {
            return "redirect:/product-list";
        }
    }

    @GetMapping("/product-delete")
    public String deleteProduct(@RequestParam("id") Long productId) {
        DatabaseDump.deleteProductById(productId);
        return "redirect:/product-list";
    }

    @GetMapping(path = {"/product/add", "/product/edit/{id}"})
    public String showForm(
            @PathVariable(name = "id", required = false) Long productId,
            Model model) {

        Product product;
        if (productId != null) {
            product = DatabaseDump.findProductById(productId, null);
            if (product == null) {
                product = new Product();
            }
        } else {
            product = new Product();
        }

        model.addAttribute("product", product);
        return "product-form";
    }
}