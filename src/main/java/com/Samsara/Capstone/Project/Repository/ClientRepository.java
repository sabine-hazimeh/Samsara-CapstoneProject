package com.Samsara.Capstone.Project.Repository;


import com.Samsara.Capstone.Project.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "SELECT * FROM client WHERE user_name = ?1", nativeQuery = true)
    Optional<Client> findUserByUserName(String userName);

    boolean existsByUserName(String userName);
    @Query(value = "select * from client where email = ?1", nativeQuery = true)
    Optional<Client> findUserByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String number);


}
