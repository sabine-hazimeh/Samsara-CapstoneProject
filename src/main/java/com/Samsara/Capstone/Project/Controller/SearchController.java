package com.Samsara.Capstone.Project.Controller;

import com.Samsara.Capstone.Project.Model.Post;
import com.Samsara.Capstone.Project.Repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final PostRepository postRepository;

    public SearchController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

@GetMapping("/posts")
public String searchPosts(
        @RequestParam(required = false) String location,
        @RequestParam(required = false) Integer bedroomNb,
        @RequestParam(required = false) Integer bathroomNb,
        Model model) {

    List<Post> searchResults;

    if (location != null && bedroomNb != null && bathroomNb != null) {
        searchResults = postRepository.findByLocationAndBedroomNbAndBathroomNb(location, bedroomNb, bathroomNb);
    } else if (location != null && bedroomNb != null) {
        searchResults = postRepository.findByLocationAndBedroomNb(location, bedroomNb);
    } else if (location != null && bathroomNb != null) {
        searchResults = postRepository.findByLocationAndBathroomNb(location, bathroomNb);
    } else if (bedroomNb != null && bathroomNb != null) {
        searchResults = postRepository.findByBedroomNbAndBathroomNb(bedroomNb, bathroomNb);
    } else if (location != null) {
        searchResults = postRepository.findByLocation(location);
    } else if (bedroomNb != null) {
        searchResults = postRepository.findByBedroomNb(bedroomNb);
    } else if (bathroomNb != null) {
        searchResults = postRepository.findByBathroomNb(bathroomNb);
    } else {
        searchResults = new ArrayList<>();
    }

    model.addAttribute("Post", searchResults);
    return "SearchPage";
}

}

