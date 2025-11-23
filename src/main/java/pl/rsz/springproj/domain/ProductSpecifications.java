package pl.rsz.springproj.domain;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ProductSpecifications {

    public static Specification<Product> hasPhrase(String phrase) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(phrase)) {
                return null;
            }
            String likePattern = "%" + phrase.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("name")), likePattern);
        };
    }

    public static Specification<Product> hasPriceBetween(Float min, Float max) {
        return (root, query, cb) -> {
            if (min != null && max != null) {
                return cb.between(root.get("price"), min, max);
            } else if (min != null) {
                return cb.greaterThanOrEqualTo(root.get("price"), min);
            } else if (max != null) {
                return cb.lessThanOrEqualTo(root.get("price"), max);
            }
            return null;
        };
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) {
                return null;
            }
            return cb.equal(root.get("category").get("id"), categoryId);
        };
    }
}