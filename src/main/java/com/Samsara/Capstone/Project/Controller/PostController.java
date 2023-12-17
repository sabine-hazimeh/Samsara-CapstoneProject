package com.Samsara.Capstone.Project.Controller;

import com.Samsara.Capstone.Project.Model.Client;
import com.Samsara.Capstone.Project.Model.Post;
import com.Samsara.Capstone.Project.Model.Report;
import com.Samsara.Capstone.Project.Model.Review;
import com.Samsara.Capstone.Project.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/Post")
@Controller
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController( PostService postService){
        this.postService = postService;
    }

    @GetMapping(value = "/display-posts")
    public String getShop(Model model) {
        model.addAttribute("Post", postService.getAllPosts());
        return "Shop";
    }

    @GetMapping(value = "/get-post/{ID}")
    public Post getPostById(@PathVariable Long ID) {
        return postService.getPostById(ID);
    }

    @GetMapping(value = "/view-post-Detail/{ID}")
    public String viewDetails(@PathVariable Long ID, Model model) {
        Post post = getPostById(ID);

        // Calculate average rating for the post
        double averageRating = post.calculateAverageRating();
        model.addAttribute("post", post);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("review", new Review());
        model.addAttribute("report", new Report());
        return "PostDetails";
    }
    @GetMapping(value = "/display-client-details/{postId}")
    public String getClientDetails(@PathVariable Long postId, Model model) {
        Post post = postService.getPostById(postId);
        Client client = post.getClient();
        Long clientId = client.getId();
        model.addAttribute("ClientId", clientId);
        model.addAttribute("Client", client);
        return "SellerDetails";
    }
}
