package com.Samsara.Capstone.Project.Service;


import com.Samsara.Capstone.Project.Model.Post;
import com.Samsara.Capstone.Project.Model.WishList;
import com.Samsara.Capstone.Project.Repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    @Autowired
    public WishListService(WishListRepository wishListRepository){
        this.wishListRepository=wishListRepository;
    }

    public WishList createWish(WishList wishList){

        return wishListRepository.save(wishList);
    }
//    public WishList getWishById(Long id) {
//        WishList originalWishList = wishListRepository.findById(id).orElse(null);
//        if (originalWishList != null) {
//            List<Post> availablePosts = originalWishList.getPostsList().stream()
//                    .filter(Post::isDeleted)
//                    .collect(Collectors.toList());
//            WishList wishListWithAvailablePosts = new WishList();
//            wishListWithAvailablePosts.setID(originalWishList.getID());
//            wishListWithAvailablePosts.setClient(originalWishList.getClient());
//            wishListWithAvailablePosts.setPostsList(availablePosts);
//
//            return wishListWithAvailablePosts;
//        }
//
//        return null;
//    }
public WishList getWishById(Long id) {
    WishList originalWishList = wishListRepository.findById(id).orElse(null);
    if (originalWishList != null) {
        List<Post> availablePosts = originalWishList.getPostsList().stream()
                .filter(post -> !post.isDeleted()) // Keep posts where isDeleted is false
                .collect(Collectors.toList());
        WishList wishListWithAvailablePosts = new WishList();
        wishListWithAvailablePosts.setID(originalWishList.getID());
        wishListWithAvailablePosts.setClient(originalWishList.getClient());
        wishListWithAvailablePosts.setPostsList(availablePosts);

        return wishListWithAvailablePosts;
    }

    return null;
}

    public void deleteItemFromWish(WishList wishList, Post post){
        wishList.getPostsList().remove(post);
        wishListRepository.save(wishList);
    }
    public void clearWishList(WishList wishList) {
        wishList.getPostsList().clear();
        wishListRepository.save(wishList);

    }
    public void addItemToWish(WishList wishList, Post post) {
        List<Post> productsList = wishList.getPostsList();
        productsList.add(post);
        wishList.setPostsList(productsList);
        wishListRepository.save(wishList);
    }
}
