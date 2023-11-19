package com.Samsara.Capstone.Project.Service;

import com.Samsara.Capstone.Project.Model.Review;
import com.Samsara.Capstone.Project.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // Save or update a review
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    // Get a review by its ID
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    // Get all reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Delete a review by its ID
    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> getReviewsByPostId(Long postId) {
        return reviewRepository.findByPostId(postId);
    }
}
