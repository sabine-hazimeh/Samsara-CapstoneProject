package com.Samsara.Capstone.Project.Security;

import com.Samsara.Capstone.Project.Model.Client;
import com.Samsara.Capstone.Project.Model.Employee;
import com.Samsara.Capstone.Project.Service.ClientDetService;
import com.Samsara.Capstone.Project.Service.EmployeeDetService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;
import java.io.IOException;

//@Component
//public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    @Autowired
//    private ClientDetService clientDetService;
//    @Autowired
//    private EmployeeDetService employeeDetService;
//    public CustomLoginSuccessHandler() {
//        setDefaultTargetUrl("/home/display-home");
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        Client client= clientDetService.findClientByUserName(userDetails.getUsername()).orElse(null);
//        Employee employee = employeeDetService.findEmployeeByUserName(userDetails.getUsername()).orElse(null);
//        if(client == null && employee == null)
//            return;
//       if (client != null) {
//            if (client.getFailedAttempt() > 0) {
//                clientDetService.resetFailedAttempts(client.getUserName());
//            }
//        } else if (employee != null) {
//            if (employee.getFailedAttempt() > 0) {
//                employeeDetService.resetFailedAttempts(employee.getUsername());
//            }
//        }
//        super.onAuthenticationSuccess(request, response, authentication);
//    }
//
//}
//
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private ClientDetService clientDetService;

    @Autowired
    private EmployeeDetService employeeDetService;

    private final RequestCache requestCache = new HttpSessionRequestCache();

    public CustomLoginSuccessHandler() {
        setDefaultTargetUrl("/home/display-home");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null && !savedRequest.getRedirectUrl().contains("/logIn/display-logIn")) {
            // Redirect to the requested URL before login
            clearAuthenticationAttributes(request);
            getRedirectStrategy().sendRedirect(request, response, savedRequest.getRedirectUrl());
        } else {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Client client = clientDetService.findClientByUserName(userDetails.getUsername()).orElse(null);
            Employee employee = employeeDetService.findEmployeeByUserName(userDetails.getUsername()).orElse(null);

            if (client == null && employee == null)
                return;

            if (client != null) {
                if (client.getFailedAttempt() > 0) {
                    clientDetService.resetFailedAttempts(client.getUserName());
                }
            } else if (employee != null) {
                if (employee.getFailedAttempt() > 0) {
                    employeeDetService.resetFailedAttempts(employee.getUsername());
                }
            }

            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
