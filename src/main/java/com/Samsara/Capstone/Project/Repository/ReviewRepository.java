package com.Samsara.Capstone.Project.Repository;


import com.Samsara.Capstone.Project.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPostId(Long postId);
}
