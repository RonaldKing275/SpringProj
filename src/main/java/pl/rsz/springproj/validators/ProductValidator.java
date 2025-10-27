package pl.rsz.springproj.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.rsz.springproj.domain.Product;

public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;

        if (product.getName() != null && product.getCategory() != null) {
            if (product.getName().equalsIgnoreCase(product.getCategory())) {
                errors.rejectValue("category", "category.identicalToName", "Kategoria nie może być taka sama jak nazwa.");
            }
        }
    }
}