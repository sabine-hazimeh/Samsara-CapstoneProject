package com.Samsara.Capstone.Project.Service;

import com.Samsara.Capstone.Project.Model.Employee;
import com.Samsara.Capstone.Project.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class EmployeeDetService {
    private final EmployeeRepository employeeRepository;

    public static final int MAX_FAILED_ATTEMPTS = 3;

    private static final long LOCK_TIME_DURATION =  60 * 1000;


    @Autowired
    public EmployeeDetService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    public Optional<Employee> findEmployeeByUserName(String username){
        return employeeRepository.findEmployeeByUsername(username);
    }
    public void increaseFailedAttempts(Employee employee) {
        int newFailAttempts = employee.getFailedAttempt() + 1;
        employee.setFailedAttempt(newFailAttempts);
        employeeRepository.save(employee);
    }

    public void lock(Employee employee) {
        employee.setAccountNonLocked(false);
        employee.setLockTime(new Date());
        employeeRepository.save(employee);
    }

    public boolean unlockWhenTimeExpired(Employee employee) {
        long lockTimeInMillis = employee.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();
        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            employee.setAccountNonLocked(true);
            employee.setLockTime(null);
            employee.setFailedAttempt(0);
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }
    public void resetFailedAttempts(String name) {
        Employee employee = employeeRepository.findEmployeeByUsername(name).orElse(null);
        if(employee == null)
            return;
        employee.setFailedAttempt(0);
        employeeRepository.save(employee);
    }

}
