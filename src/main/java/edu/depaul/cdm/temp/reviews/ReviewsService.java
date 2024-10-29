package edu.depaul.cdm.temp.reviews;

import edu.depaul.cdm.temp.user.User;
import edu.depaul.cdm.temp.inventory.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewsService {

    private final ReviewsRepository reviewsRepository;

    @Autowired
    public ReviewsService(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    public List<Reviews> getAllReviews() {
        return reviewsRepository.findAll();
    }

    public Optional<Reviews> getReviewById(Long id) {
        return reviewsRepository.findById(id);
    }

    public Reviews createReview(String content, int rating, User user, Inventory inventory) {
        Reviews review = new Reviews(content, rating, user, inventory);
        return reviewsRepository.save(review);
    }

    public Reviews updateReview(Long id, String content, int rating) {
        Optional<Reviews> optionalReview = reviewsRepository.findById(id);
        if (optionalReview.isPresent()) {
            Reviews review = optionalReview.get();
            review.setContent(content);
            review.setRating(rating);
            return reviewsRepository.save(review);
        } else {
            throw new RuntimeException("Review not found with id: " + id);
        }
    }

    public void deleteReview(Long id) {
        if (reviewsRepository.existsById(id)) {
            reviewsRepository.deleteById(id);
        } else {
            throw new RuntimeException("Review not found with id: " + id);
        }
    }
}
