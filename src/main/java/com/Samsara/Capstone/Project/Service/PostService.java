package com.Samsara.Capstone.Project.Service;

import com.Samsara.Capstone.Project.Model.*;
import com.Samsara.Capstone.Project.Repository.ClientRepository;
import com.Samsara.Capstone.Project.Repository.PostPictureRepo;
import com.Samsara.Capstone.Project.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostPictureRepo postPictureRepo;
    private final ClientRepository clientRepository;
    private final ReviewService reviewService;
    private final ReportService reportService;

    @Autowired
    public PostService(PostRepository postRepository, ReviewService reviewService,ReportService reportService, PostPictureRepo postPictureRepo, ClientRepository clientRepository) {
       this.postRepository=postRepository;
       this.postPictureRepo = postPictureRepo;
       this.clientRepository = clientRepository;
       this.reviewService = reviewService;
       this.reportService = reportService;
    }

    public List<Post> getAllPosts(){
        return postRepository.findByDeleted();
    }

    public Post getPostById(Long id){

        return postRepository.findById(id).orElse(null);
    }


public Post createPost(Post post, List<MultipartFile> files) throws IOException {
       List<PostPicture> postPictures = new ArrayList<>();
       for(MultipartFile file : files){
           PostPicture postPicture = new PostPicture(Base64.getEncoder().encodeToString(file.getBytes()));
           postPictures.add(postPictureRepo.save(postPicture));
       }
       post.setImages(postPictures);
       return postRepository.save(post);
}


    public Post updatePost(Post post, List<MultipartFile> files) throws IOException {
        List<PostPicture> existingImages = post.getImages();
        if (existingImages != null && !existingImages.isEmpty()) {
            for (PostPicture image : existingImages) {
                postPictureRepo.delete(image);
            }
            existingImages.clear();
        }
        List<PostPicture> postPictures = new ArrayList<>();
        for(MultipartFile file : files){
            PostPicture postPicture = new PostPicture(Base64.getEncoder().encodeToString(file.getBytes()));
            postPictures.add(postPictureRepo.save(postPicture));
        }
        post.setImages(postPictures);
        List<Review> existingReviews = reviewService.getReviewsByPostId(post.getId());
        List<Report> existingReports = reportService.getReportsByPostId(post.getId());
        post.setReviews(existingReviews);
        post.setReports(existingReports);
        return postRepository.save(post);
    }

//    public void deletePostById(Long id) {
//        postRepository.deleteById(id);
//    }
public void DeletePost(Long id){
    Post post = getPostById(id);
    post.setDeleted(true);
    postRepository.save(post);
}
    public List<Post> getPostsByClientId(Long clientId, boolean deleted) {
        return postRepository.findByClientIdAndDeleted(clientId, deleted);
    }
//    public void deletePost(Long id){
//        postRepository.deleteById(id );
//    }

}
