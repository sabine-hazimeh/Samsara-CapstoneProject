package com.Samsara.Capstone.Project.Repository;

import com.Samsara.Capstone.Project.Model.PostPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPictureRepo extends JpaRepository<PostPicture, Long> {
}
