package com.Samsara.Capstone.Project.Repository;

import com.Samsara.Capstone.Project.Model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
}