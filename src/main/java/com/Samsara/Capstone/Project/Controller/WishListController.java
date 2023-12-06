package com.Samsara.Capstone.Project.Controller;

import com.Samsara.Capstone.Project.Model.Client;
import com.Samsara.Capstone.Project.Model.Post;
import com.Samsara.Capstone.Project.Model.WishList;
import com.Samsara.Capstone.Project.Security.UserInfoDetails;
import com.Samsara.Capstone.Project.Service.ClientService;
import com.Samsara.Capstone.Project.Service.PostService;
import com.Samsara.Capstone.Project.Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wish")
public class WishListController {
    private final ClientService clientService;
    private final WishListService wishListService;
    private final PostService postService;

    @Autowired
    public WishListController(ClientService clientService, WishListService wishListService, PostService postService) {
        this.clientService = clientService;
        this.wishListService = wishListService;
        this.postService = postService;
    }

    @GetMapping(value = "/display-wish")
    public String getWish(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        Client client = clientService.getClientById(userInfoDetails.getUserId());
        WishList wishList = wishListService.getWishById(client.getWishList().getID());
        model.addAttribute("wishList", wishList);

        return "WishList";
    }

    @PostMapping("/add/{postId}")
    public String addItemToWish(@PathVariable("postId") Long postId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        Client client = clientService.getClientById(userInfoDetails.getUserId());
        WishList wishList = wishListService.getWishById(client.getWishList().getID());
        wishList.setClient(client);
        Post post = postService.getPostById(postId);
        wishListService.addItemToWish(wishList, post);
        return "redirect:/wish/display-wish";
    }

    @GetMapping("/delete-WishItem/{id}")

    public String deleteFromWish(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        Client client = clientService.getClientById(userInfoDetails.getUserId());
        Post post = postService.getPostById(id);
        wishListService.deleteItemFromWish(client.getWishList(), post);
        return "redirect:/wish/display-wish";
    }

    @GetMapping("/clear/{Id}")
    public String clearAllWish(@PathVariable Long Id) {
        WishList wishList = wishListService.getWishById(Id);
        wishListService.clearWishList(wishList);
        return "redirect:/wish/display-wish";

    }

}