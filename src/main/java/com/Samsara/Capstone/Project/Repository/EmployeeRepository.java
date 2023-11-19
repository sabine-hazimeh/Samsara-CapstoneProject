package com.Samsara.Capstone.Project.Repository;

import com.Samsara.Capstone.Project.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String number);
    @Query(value = "SELECT * FROM employee WHERE username = ?1", nativeQuery = true)
    Optional<Employee> findEmployeeByUsername(String username);
    boolean existsByUsername(String username);
}

