package com.Samsara.Capstone.Project.Controller;

import com.Samsara.Capstone.Project.Model.*;
import com.Samsara.Capstone.Project.Repository.ReportRepository;
import com.Samsara.Capstone.Project.Security.UserInfoDetails;
import com.Samsara.Capstone.Project.Service.ClientService;
import com.Samsara.Capstone.Project.Service.PostService;
import com.Samsara.Capstone.Project.Service.ReportService;
import com.Samsara.Capstone.Project.Service.ReviewService;
import com.Samsara.Capstone.Project.enums.PostStatus;
import com.Samsara.Capstone.Project.enums.WaterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/post")
@Controller
public class PostAuthController {
    private final PostService postService;
    private final ClientService clientService;
    private final ReviewService reviewService;
    private final ReportService reportService;

    @Autowired
    public PostAuthController(PostService postService, ClientService clientService, ReviewService reviewService, ReportService reportService) {
        this.clientService = clientService;
        this.postService = postService;
        this.reviewService = reviewService;
        this.reportService = reportService;

    }

    @PostMapping(value = "/add-review/{postId}")
    public String addReview(@PathVariable Long postId, Review review, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        Client client = clientService.getClientById(userInfoDetails.getUserId());
        review.setClient(client);
        Post post = postService.getPostById(postId);
        review.setPost(post);
        reviewService.createReview(review);

        return "redirect:/Post/view-post-Detail/" + postId;
    }
    @PostMapping(value = "/add-report/{postId}")
    public String addReport(@PathVariable Long postId, Report report, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        Client client = clientService.getClientById(userInfoDetails.getUserId());
        report.setClient(client);
        Post post = postService.getPostById(postId);
        report.setPost(post);
        reportService.createReport(report);

        return "redirect:/Post/view-post-Detail/" + postId;
    }
    @GetMapping(value = "/add-post")
    public String showPostForm(Model model) {
        model.addAttribute("Post", new Post());
        model.addAttribute("poststat", PostStatus.values());
        model.addAttribute("watertype", WaterType.values());
        return "CreatePost";
    }
    @GetMapping(value="/predict-price")
    public String predictPostPrice(Model model){
        model.addAttribute("predict", new PredictionInput());
        return "PredictionForm";
    }


    @PostMapping(value = "/add-post-to-list")
    @PreAuthorize("hasAnyAuthority('client')")
    public String addPost(@ModelAttribute Post post, @RequestParam("imageFiles") List<MultipartFile> imageFiles, Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        Client client = clientService.getClientById(userInfoDetails.getUserId());
        post.setClient(client);
        postService.createPost(post, imageFiles);
        return "redirect:/Post/display-posts";
    }
    @GetMapping(value = "/update-post/{id}")
    public String UpdateProducts(@PathVariable Long id, Model model) {
        model.addAttribute("Post", postService.getPostById(id));
        return "UpdatePost";

    }



    @PostMapping(value = "/update-post")
    @PreAuthorize("hasAnyAuthority('client')")
    public String updatePost(@ModelAttribute Post post, @RequestParam("imageFiles") List<MultipartFile> imageFiles, Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        Client client = clientService.getClientById(userInfoDetails.getUserId());
        post.setClient(client);
        postService.updatePost(post, imageFiles);
        return "redirect:/post/myposts";
    }
    @GetMapping(value = "/myposts")
    public String getMyPosts(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        Client client = clientService.getClientById(userInfoDetails.getUserId());
        if (client != null) {
            List<Post> myPosts = postService.getPostsByClientId(client.getId(),false);
            model.addAttribute("posts", myPosts);
        } else {
            model.addAttribute("errorMessage", "Client not found");
        }
        return "MyPosts";
    }

    @GetMapping("/delete-post/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.DeletePost(id);
        return "redirect:/post/myposts";
    }


}