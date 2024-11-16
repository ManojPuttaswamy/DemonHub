package edu.depaul.cdm.demonhub.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.depaul.cdm.demonhub.inventory.Inventory;
import edu.depaul.cdm.demonhub.user.User;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {

    private final ReviewsService reviewsService;

    @Autowired
    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    @GetMapping
    public ResponseEntity<List<Reviews>> getAllReviews() {
        List<Reviews> reviews = reviewsService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable Long id) {
        Optional<Reviews> review = reviewsService.getReviewById(id);
        return review.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Reviews> createReview(
            @RequestParam String content,
            @RequestParam int rating,
            @RequestParam User user,
            @RequestParam Inventory inventory) {
        try {
            Reviews review = reviewsService.createReview(content, rating, user, inventory);
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reviews> updateReview(
            @PathVariable Long id,
            @RequestParam String content,
            @RequestParam int rating) {
        try {
            Reviews updatedReview = reviewsService.updateReview(id, content, rating);
            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        try {
            reviewsService.deleteReview(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
