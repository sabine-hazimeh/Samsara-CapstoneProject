package com.Samsara.Capstone.Project.Security;

import com.Samsara.Capstone.Project.Model.Client;
import com.Samsara.Capstone.Project.Model.Employee;
import com.Samsara.Capstone.Project.Service.ClientDetService;
import com.Samsara.Capstone.Project.Service.EmployeeDetService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ClientDetService clientDetService;
    @Autowired
    private EmployeeDetService employeeDetService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        Optional<Client> client = clientDetService.findClientByUserName(username);
        Optional<Employee> employee = employeeDetService.findEmployeeByUserName(username);
        if (client.isPresent()) {
            Client client2 = client.get();
            if (client2.isAccountNonLocked()) {
                if (client2.getFailedAttempt() < ClientDetService.MAX_FAILED_ATTEMPTS - 1) {
                    clientDetService.increaseFailedAttempts(client2);
                    exception = new LockedException("You still have "+(ClientDetService.MAX_FAILED_ATTEMPTS-client2.getFailedAttempt())+" attempt otherwise your account will be locked");
                } else {
                   clientDetService.lock(client2);
                    exception = new LockedException("Your account has been locked due to 3 failed attempts."
                            + " It will be unlocked after 1 min");
                }
            } else{
                if (clientDetService.unlockWhenTimeExpired(client2)) {
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
                }
            }

        }
        else if (employee.isPresent()) {
            Employee employee1 =employee.get();
            if (employee1.isAccountNonLocked()) {
                if (employee1.getFailedAttempt() < EmployeeDetService.MAX_FAILED_ATTEMPTS - 1) {
                    employeeDetService.increaseFailedAttempts(employee1);
                    exception = new LockedException("You still have "+(EmployeeDetService.MAX_FAILED_ATTEMPTS-employee1.getFailedAttempt())+" attempt otherwise your account will be locked");
                } else {
                    employeeDetService.lock(employee1);
                    exception = new LockedException("Your account has been locked due to 3 failed attempts."
                            + " It will be unlocked after 1 min");
                }
            } else{
                if (employeeDetService.unlockWhenTimeExpired(employee1)) {
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
                }
            }
        }
        super.setDefaultFailureUrl("/logIn/error/"+exception.getMessage());
        super.onAuthenticationFailure(request, response, exception);
    }

}
