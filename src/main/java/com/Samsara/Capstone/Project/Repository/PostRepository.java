package com.Samsara.Capstone.Project.Repository;

import com.Samsara.Capstone.Project.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByClientId(Long clientId);
    @Query(value="SELECT * FROM Post p WHERE p.location = :location AND p.bedroom_nb = :bedroomNb AND p.bathroom_nb = :bathroomNb", nativeQuery = true)
    List<Post> findByLocationAndBedroomNbAndBathroomNb(String location, Integer bedroomNb, Integer bathroomNb);

    @Query(value="SELECT * FROM Post p WHERE p.location = :location AND p.bedroom_nb = :bedroomNb", nativeQuery = true)
    List<Post> findByLocationAndBedroomNb(String location, Integer bedroomNb);

    @Query(value="SELECT * FROM Post p WHERE p.location = :location AND p.bathroom_nb = :bathroomNb", nativeQuery = true)
    List<Post> findByLocationAndBathroomNb(String location, Integer bathroomNb);

    @Query(value="SELECT * FROM Post p WHERE p.bedroomNb = :bedroomNb AND p.bathroom_nb = :bathroomNb", nativeQuery = true)
    List<Post> findByBedroomNbAndBathroomNb(Integer bedroomNb, Integer bathroomNb);

    @Query(value="SELECT * FROM Post p WHERE p.location = :location", nativeQuery = true)
    List<Post> findByLocation(String location);

    @Query(value="SELECT * FROM Post p WHERE p.bedroom_nb = :bedroomNb", nativeQuery = true)
    List<Post> findByBedroomNb(Integer bedroomNb);

    @Query(value="SELECT * FROM Post p WHERE p.bathroom_nb = :bathroomNb", nativeQuery = true)
    List<Post> findByBathroomNb(Integer bathroomNb);

}
