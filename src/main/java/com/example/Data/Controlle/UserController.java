
package com.example.Data.Controlle;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Data.Configuration.S3Configuration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.Data.Domain.Followers;
import com.example.Data.Domain.User;

import com.example.Data.Repository.FollowersRepository;
import com.example.Data.Repository.UserRepository;
import com.example.Data.Service.UserService;

import jakarta.websocket.server.PathParam;

@RestController
@CrossOrigin
@RequestMapping("/aapi")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private FollowersRepository followRepo;
	
	@Autowired
    private UserService srv;
	
	
	@GetMapping("/users")
	public Iterable<User> getAllUsers(){
		return userRepo.findAll();
	}
	
	@GetMapping("/users/{id}")
	public Optional<User> getuser(@PathVariable Long id){
		return userRepo.findById(id);
	}
	
	
	@GetMapping("/users/username/{id}")
	public User getUsers(@PathVariable String id){
		return userRepo.findByUserName(id);
	}
	
	@PostMapping("/register")
	public void addUser(@RequestParam("email") String email , @RequestParam("name") String name , @RequestParam("password") String password , @RequestParam("user_name") String user_name , @RequestParam("profile") MultipartFile profile) throws IOException {
	 srv.addUser( email, name, password, user_name, profile);
	 System.out.print(name+"this is typed name");
	}
	
    @PostMapping("/{followerId}/follow/{followingId}")
    public ResponseEntity<String> followUser(@PathVariable Long followerId, @PathVariable Long followingId) {
        try {
			srv.followUser(followerId, followingId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ResponseEntity.ok("User followed successfully");
    }

    @PostMapping("/{followerId}/unfollow/{followingId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long followerId, @PathVariable Long followingId) {
        srv.unfollowUser(followerId, followingId);
        return ResponseEntity.ok("User unfollowed successfully");
    }
    
    @GetMapping("/users/{id}/followers")
    public List<Followers> getFollowers(@PathVariable   Long id){
    	return srv.findByUserId(id);
    }
    
    @GetMapping("/users/{id}/followers/count")
    public int noOf(@PathVariable Long id) { 
    	return srv.howMuch(id);
    }
    
    @GetMapping("/users/{loggedInId}/isFollowing/{targetUserId}")
    public boolean isFollowing(@PathVariable Long loggedInId, @PathVariable Long targetUserId) {
        return followRepo.existsByFollowerIdAndFollowingId(loggedInId, targetUserId);
    }
    
    @GetMapping("/users/{id}/following/count")
    public int nofollowing(@PathVariable Long id) { 
    	return srv.howMuchf(id);
    }
    @GetMapping("/users/{id}/following")
    public List<Followers> following(@PathVariable Long id) { 
    	return srv.findByUserfId(id);
    }
    



	
	
}

