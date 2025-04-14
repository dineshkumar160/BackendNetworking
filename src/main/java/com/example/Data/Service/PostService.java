package com.example.Data.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.example.Data.Domain.Post;
import com.example.Data.Domain.User;
import com.example.Data.Repository.PostRepository;
import com.example.Data.Repository.UserRepository;

@Service
public class PostService {

   
    
    @Autowired
    private UserRepository userr;
    
    @Autowired
    private PostRepository repo;

    @Autowired
    private S3Service s3Service;
    
    
    

    public void uploadPost(MultipartFile image, String desc, Long userid) {
        String imageUrl = s3Service.uploadFile(image); 

    	Post post=new Post();
		post.setDesc(desc);
		post.setImage(imageUrl);
		
		Optional<User> user=userr.findById(userid);
		User use=user.get();
		post.setUser(use);
		repo.save(post);
    }


	public List<Post> getPostByUserId(long id) {
		return repo.findPostsByUserId(id);
		
	}


	public List<Post> findAllPosts() {
		return (List<Post>) repo.findAll();
	}


	public void uploadPost(MultipartFile image, String desc, String username) {
		   String imageUrl = s3Service.uploadFile(image); 

	    	Post post=new Post();
			post.setDesc(desc);
			post.setImage(imageUrl);
			
			User user=userr.findByUserName(username);
		
			post.setUser(user);
			repo.save(post);
		
	}


	
}
