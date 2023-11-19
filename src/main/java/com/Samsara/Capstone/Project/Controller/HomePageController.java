package com.Samsara.Capstone.Project.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/home")
@Controller
public class HomePageController {
    @GetMapping(value = "/display-home")
    public String getHomePage(Model model)  {
        return "HomePage";
    }

}
