package com.Samsara.Capstone.Project.Service;

import com.Samsara.Capstone.Project.Model.Post;
import com.Samsara.Capstone.Project.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
       this.postRepository=postRepository;
    }

    public List<Post> getAllPosts(){

        return postRepository.findAll();
    }

    public Post getPostById(Long id){

        return postRepository.findById(id).orElse(null);
    }


//    public Post createPost(Post post) {
//        return postRepository.save(post);
//    }
public Post createPost(Post post, List<MultipartFile> files) throws IOException {
    List<String> imageList = new ArrayList<>();

    for (MultipartFile file : files) {
        imageList.add(Base64.getEncoder().encodeToString(file.getBytes()));
    }
    post.setPictures(imageList);

    return postRepository.save(post);
}
    public List<Post> getPostsByClientId(Long clientId) {
        return postRepository.findByClientId(clientId);
    }

public Post updatePost(Post updatedPost, MultipartFile[] files) {
    Post existingPost = postRepository.findById(updatedPost.getId()).orElse(null);

    if (existingPost != null) {
        List<String> existingPictures = existingPost.getPictures();
        if (files != null && files.length > 0) {
            List<String> uploadedFileNames = new ArrayList<>();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    uploadedFileNames.add(file.getOriginalFilename());
                }
            }
            existingPictures.addAll(uploadedFileNames);
        }
        existingPost.setPictures(existingPictures);
        return postRepository.save(existingPost);
    } else {
        return null;
    }
}

    public void deletePost(Long id){
        postRepository.deleteById(id );
    }

}
