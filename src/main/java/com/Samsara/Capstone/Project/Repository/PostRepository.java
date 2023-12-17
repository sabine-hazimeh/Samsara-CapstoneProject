package com.Samsara.Capstone.Project.Repository;

import com.Samsara.Capstone.Project.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByClientIdAndDeleted(Long clientId, boolean deleted);
    @Query(value="SELECT * FROM Post p WHERE p.deleted = false", nativeQuery = true)
    List<Post> findByDeleted();
    List<Post> findByLocationAndBedroomNbAndBathroomNbAndDeleted(String location, Integer bedroomNb, Integer bathroomNb, boolean deleted);
    List<Post> findByLocationAndBedroomNbAndDeleted(String location, Integer bedroomNb, boolean deleted);
    List<Post> findByLocationAndBathroomNbAndDeleted(String location, Integer bathroomNb, boolean deleted);
    List<Post> findByBedroomNbAndBathroomNbAndDeleted(Integer bedroomNb, Integer bathroomNb, boolean deleted);
    List<Post> findByLocationAndDeleted(String location, boolean deleted);
    List<Post> findByBedroomNbAndDeleted(Integer bedroomNb, boolean deleted);
    List<Post> findByBathroomNbAndDeleted(Integer bathroomNb, boolean deleted);
    long count();
    long countByAvailableFalse();
    long countByAvailableTrue();
}
