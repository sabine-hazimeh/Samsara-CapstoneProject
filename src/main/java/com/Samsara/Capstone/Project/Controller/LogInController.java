package com.Samsara.Capstone.Project.Controller;
import com.Samsara.Capstone.Project.Service.ClientService;
import com.Samsara.Capstone.Project.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import  com.Samsara.Capstone.Project.Model.Client;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/logIn")
@Controller
public class LogInController {

    @GetMapping(value = "/display-logIn")
    public String getLogIn(Model model){
        return "LogIn";
    }

    @GetMapping(value = "/error/{message}")
    public String getError(@PathVariable("message") String message, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errorMessage", message);
        return "redirect:/logIn/display-logIn";
    }




}
