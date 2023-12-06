package com.Samsara.Capstone.Project.Security;

import com.Samsara.Capstone.Project.Model.Client;
import com.Samsara.Capstone.Project.Model.Employee;
import com.Samsara.Capstone.Project.Service.ClientService;
import com.Samsara.Capstone.Project.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
public class UserInfoDetailsService implements UserDetailsService {
    @Autowired
    private ClientService clientService;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String credential) throws UsernameNotFoundException {
        Optional<Client> client = clientService.findClientByUserName(credential);
        if (client.isPresent()) {
            return new UserInfoDetails(client.get());
        } else {
            Optional<Employee> employee = employeeService.findEmployeeByUserName(credential);
            if (employee.isPresent()) {
                return new UserInfoDetails(employee.get());
            } else {
                throw new UsernameNotFoundException("The user credential is not found");
            }
        }
    }
}
