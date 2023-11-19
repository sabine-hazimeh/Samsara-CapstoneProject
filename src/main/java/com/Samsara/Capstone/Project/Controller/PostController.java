package com.Samsara.Capstone.Project.Controller;

import com.Samsara.Capstone.Project.Model.Client;
import com.Samsara.Capstone.Project.Model.Post;
import com.Samsara.Capstone.Project.Model.Report;
import com.Samsara.Capstone.Project.Model.Review;
import com.Samsara.Capstone.Project.Security.UserInfoDetails;
import com.Samsara.Capstone.Project.Service.ClientService;
import com.Samsara.Capstone.Project.Service.PostService;
import com.Samsara.Capstone.Project.Service.ReviewService;
import com.Samsara.Capstone.Project.enums.PostStatus;
import com.Samsara.Capstone.Project.enums.WaterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/post")
@Controller
public class PostController {
    private final PostService postService;
    private final ClientService clientService;
    private final ReviewService reviewService;
    @Autowired
    public PostController(PostService postService,ClientService clientService, ReviewService reviewService)
    {
        this.clientService=clientService;
       this.postService = postService;
       this.reviewService = reviewService;
    }
    @GetMapping(value = "/display-posts")
    public String getShop(Model model)  {
        model.addAttribute("Post", postService.getAllPosts());
        return "Shop";
    }

    @GetMapping(value = "/get-post/{ID}")
    public Post getPostById(@PathVariable Long ID) {
        return postService.getPostById(ID);
    }

    @GetMapping(value = "/view-post-Detail/{ID}")
    public String viewDetails(@PathVariable Long ID, Model model) {
        model.addAttribute("post",getPostById(ID));
       // List<Review> review = reviewService.getReviewsByPostId(ID);
        model.addAttribute("review", new Review());
        return "PostDetails";
    }
    @PostMapping(value="/add-review/{postId}")
    public String addReview(@PathVariable Long postId, Review review, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        Client client = clientService.getClientById(userInfoDetails.getUserId());
        review.setClient(client);
        Post post = postService.getPostById(postId);
        review.setPost(post);
        reviewService.createReview(review);

        return "redirect:/post/view-post-Detail/" + postId;
    }
    @GetMapping(value="/add-post")
    public String showPostForm(Model model){
        model.addAttribute("Post",new Post());
        model.addAttribute("poststat", PostStatus.values());
        model.addAttribute("watertype", WaterType.values());

        return "CreatePost";
    }



    //@PostMapping(value = "/add-post-to-list")
//public String addProduct(@ModelAttribute("Post") Post post, @RequestParam("files") MultipartFile[] files, Authentication authentication) {
//    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//    UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
//    Client client = clientService.getClientById(userInfoDetails.getUserId());
//    post.setClient(client);
//    if (files != null && files.length > 0) {
//        List<String> uploadedFileNames = new ArrayList<>();
//        for (MultipartFile file : files) {
//            if (!file.isEmpty()) {
//                uploadedFileNames.add(file.getOriginalFilename());
//            }
//        }
//        post.getPictures().addAll(uploadedFileNames);
//    }
//    postService.createPost(post);
//
//    return "redirect:/post/display-posts";
//}
@PostMapping(value = "/add-post-to-list")
public String addPost(@ModelAttribute Post post, @RequestParam("files") List<MultipartFile> files, Authentication authentication) throws IOException {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
    Client client = clientService.getClientById(userInfoDetails.getUserId());
    post.setClient(client);
    postService.createPost(post, files);
    return "redirect:/post/display-posts";
}



    @GetMapping(value = "/{postId}/image")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Long postId) {
        Optional<Post> postOptional = Optional.ofNullable(postService.getPostById(postId));
        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            // Assuming `post.getPictures()` returns a List<String> of Base64 encoded images.
            List<String> pictures = post.getPictures();

            // Retrieving the first image (you might want to retrieve a specific image by index).
            if (!pictures.isEmpty()) {
                String base64Image = pictures.get(0); // Get the first image; you may adjust this logic.

                byte[] imageBytes = java.util.Base64.getDecoder().decode(base64Image);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/create-product")
    public Post createPost(@RequestBody Post post, @RequestParam("files") List<MultipartFile> files) throws IOException{
        return postService.createPost(post, files);
    }

    @GetMapping(value="/myposts")
    public String getMyPosts(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        Client client = clientService.getClientById(userInfoDetails.getUserId());
        if (client != null) {
            List<Post> myPosts = postService.getPostsByClientId(client.getId());
            model.addAttribute("posts", myPosts);
        } else {
            model.addAttribute("errorMessage", "Client not found");
        }
        return "MyPosts";
    }
    @GetMapping(value="/viewClientDetails/{clientId}")
    public String getClientDetails(Model model, @PathVariable Long clientId){
        model.addAttribute("client",clientService.getClientById(clientId));
        return "ClientDetails";
    }
    @GetMapping(value = "/update-post/{id}")
    public String UpdateProducts(@PathVariable Long id,Model model) {
        model.addAttribute("post",getPostById(id));
        return "UpdatePost";

    }

//@PostMapping(value = "/update-post")
//public String updatePost(@ModelAttribute("post") Post updatedPost,
//                         @RequestParam("files") MultipartFile[] files) {
//    Post existingPost = postService.getPostById(updatedPost.getId());
//    updatedPost.setClient(existingPost.getClient());
//    List<String> existingPictures = existingPost.getPictures();
//
//
//    if (files != null && files.length > 0) {
//        List<String> uploadedFileNames = new ArrayList<>();
//        for (MultipartFile file : files) {
//            if (!file.isEmpty()) {
//                uploadedFileNames.add(file.getOriginalFilename());
//            }
//        }
//        existingPictures.addAll(uploadedFileNames);
//    }
//    updatedPost.setPictures(existingPictures);
//    postService.createPost(updatedPost,files);
//    postService.updatePost(updatedPost,files);
//    return "redirect:/post/myposts";
//}

    @GetMapping("/delete-post/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/post/myposts";
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
//    @PostMapping("/add-review")
//    public String addReviewToPost(@RequestParam Long postId, @RequestParam int rate, @RequestParam String description,
//                                  Model model, Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
//        Client client = clientService.getClientById(userInfoDetails.getUserId());
//        // Fetch the post by postId from your database
//        Post post = postService.getPostById(postId);
//
//        if (post != null) {
//            Review review = new Review();
//            review.setRate(rate);
//            review.setDescription(description);
//            review.setPost(post);
//            review.setClient(client);
//            post.getReviews().add(review);
//            postService.savePost(post);
//
//            // Redirect back to the property details page or any appropriate page
//            return "redirect:/post/view-post-Detail/" + postId;
//
//        } else {
//            model.addAttribute("error", "Post not found");
//            return "redirect:/post/view-post-Detail/" + postId;
//        }
//    }

}
