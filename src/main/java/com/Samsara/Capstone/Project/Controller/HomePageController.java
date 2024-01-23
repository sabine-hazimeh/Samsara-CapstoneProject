package com.Samsara.Capstone.Project.Controller;

import com.Samsara.Capstone.Project.Repository.ClientRepository;
import com.Samsara.Capstone.Project.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/home")
@Controller
public class HomePageController {
    private final ClientRepository clientRepository;
    private final PostRepository postRepository;

    @Autowired
    public HomePageController(ClientRepository clientRepository, PostRepository postRepository) {
        this.clientRepository = clientRepository;
        this.postRepository = postRepository;
    }
    @GetMapping(value = "/display-home")
    public String getHomePage(Model model)  {
        long clientCount = clientRepository.count();
        model.addAttribute("clientCount", clientCount);
        long countOfAvailablePosts = postRepository.countByAvailableTrue();
        model.addAttribute("availablePostCount", countOfAvailablePosts);
        long countOfUnavailablePosts = postRepository.countByAvailableFalse();
        model.addAttribute("unavailablePostCount", countOfUnavailablePosts);
        return "HomePage";
    }
}
