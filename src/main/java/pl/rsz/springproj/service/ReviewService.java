package pl.rsz.springproj.service;

import org.springframework.stereotype.Service;
import pl.rsz.springproj.domain.Product;
import pl.rsz.springproj.domain.Review;
import pl.rsz.springproj.domain.User;
import pl.rsz.springproj.repositories.ProductRepository;
import pl.rsz.springproj.repositories.ReviewRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    public void addReview(User author, Long productId, Review review) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono produktu"));

        review.setAuthor(author);
        review.setProduct(product);
        review.setCreatedDate(LocalDateTime.now());

        reviewRepository.save(review);
    }

    public List<Review> getReviewsForProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
}