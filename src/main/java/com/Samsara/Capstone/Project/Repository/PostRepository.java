package com.Samsara.Capstone.Project.Repository;

import com.Samsara.Capstone.Project.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByClientIdAndDeleted(Long clientId, boolean deleted);
    @Query(value="SELECT * FROM Post p WHERE p.deleted = false", nativeQuery = true)
    List<Post> findByDeleted();
    @Query(value = "SELECT * FROM Post p WHERE LOWER(p.location) LIKE %:location% AND p.bedroom_nb = :bedroomNb AND p.bathroom_nb = :bathroomNb AND p.deleted = :deleted", nativeQuery = true)
    List<Post> findByPartialLocationAndBedroomNbAndBathroomNbAndDeleted(
            @Param("location") String location,
            @Param("bedroomNb") Integer bedroomNb,
            @Param("bathroomNb") Integer bathroomNb,
            @Param("deleted") boolean deleted
    );

    @Query(value = "SELECT * FROM Post p WHERE LOWER(p.location) LIKE %:location% AND p.bedroom_nb = :bedroomNb AND p.deleted = :deleted", nativeQuery = true)
    List<Post> findByPartialLocationAndBedroomNbAndDeleted(
            @Param("location") String location,
            @Param("bedroomNb") Integer bedroomNb,
            @Param("deleted") boolean deleted
    );

    @Query(value = "SELECT * FROM Post p WHERE LOWER(p.location) LIKE %:location% AND p.bathroom_nb = :bathroomNb AND p.deleted = :deleted", nativeQuery = true)
    List<Post> findByPartialLocationAndBathroomNbAndDeleted(
            @Param("location") String location,
            @Param("bathroomNb") Integer bathroomNb,
            @Param("deleted") boolean deleted
    );
    List<Post> findByBedroomNbAndBathroomNbAndDeleted(Integer bedroomNb, Integer bathroomNb, boolean deleted);
//    List<Post> findByLocationAndDeleted(String location, boolean deleted);
@Query(value = "SELECT * FROM Post p WHERE LOWER(p.location) LIKE %:location% AND p.deleted = :deleted", nativeQuery = true)
List<Post> findByPartialLocationAndDeleted(@Param("location") String location, @Param("deleted") boolean deleted);
    List<Post> findByBedroomNbAndDeleted(Integer bedroomNb, boolean deleted);
    List<Post> findByBathroomNbAndDeleted(Integer bathroomNb, boolean deleted);
    long count();
    long countByAvailableFalse();
    long countByAvailableTrue();
}
