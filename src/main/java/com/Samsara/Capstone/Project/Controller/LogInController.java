package com.Samsara.Capstone.Project.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/logIn")
@Controller
public class LogInController {
    @GetMapping(value = "/display-logIn")
    public String getLogIn(Model model){
        return "LogIn";
    }

}
