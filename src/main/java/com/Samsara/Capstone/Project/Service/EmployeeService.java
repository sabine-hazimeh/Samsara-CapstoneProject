package com.Samsara.Capstone.Project.Service;

import com.Samsara.Capstone.Project.Model.Employee;
import com.Samsara.Capstone.Project.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    final public EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder){
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Optional<Employee> findEmployeeByFullName(String firstName, String lastName) {
        return employeeRepository.findByFirstNameAndLastName(firstName, lastName);
    }
    public Employee createEmployee(Employee employee){
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }
    public Boolean userEmailExists(String email){
        return employeeRepository.existsByEmail(email);
    }
    public Boolean userPhoneNumberExists(String number){
        return employeeRepository.existsByPhoneNumber(number);
    }
    public Optional<Employee> findEmployeeByUserName(String username){
        return employeeRepository.findEmployeeByUsername(username);
    }
    public Boolean userNameExists(String userName){
        return employeeRepository.existsByUsername(userName);
    }
}
