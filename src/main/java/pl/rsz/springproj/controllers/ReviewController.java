package pl.rsz.springproj.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.rsz.springproj.domain.Review;
import pl.rsz.springproj.domain.User;
import pl.rsz.springproj.repositories.UserRepository;
import pl.rsz.springproj.service.ReviewService;

@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;

    public ReviewController(ReviewService reviewService, UserRepository userRepository) {
        this.reviewService = reviewService;
        this.userRepository = userRepository;
    }

    @PostMapping("/review/add")
    public String addReview(
            @ModelAttribute Review review,
            @RequestParam Long productId,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername());
        reviewService.addReview(user, productId, review);

        return "redirect:/product-details?id=" + productId;
    }
}