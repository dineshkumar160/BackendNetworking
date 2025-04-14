package com.example.Data.Controlle;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Data.Domain.Post;

import com.example.Data.Service.PostService;

@RestController
@CrossOrigin
@RequestMapping("/aapi/users")
public class PostController {
      
	
	

    @Autowired 
	private PostService serve;
	
	@GetMapping("/{userId}/post")
	
	public List<Post> getPostsByUserid(@PathVariable long userId){
		return  serve.getPostByUserId(userId);
	}
	
	@GetMapping("/post") 
	
	public List<Post> getAllPosts(){
		return serve.findAllPosts();
	}
	
	@PostMapping("{username}/add")
	public  void addPost(@PathVariable String username,@RequestParam("caption") String desc,@RequestParam("image") MultipartFile  image ) {
	serve.uploadPost(image, desc,username);
		
	}
	
}
