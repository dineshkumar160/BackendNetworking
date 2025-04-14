package com.example.Data.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.example.Data.Domain.Followers;
import com.example.Data.Domain.User;
import com.example.Data.Repository.FollowersRepository;
import com.example.Data.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private S3Service service;

    @Autowired
    private FollowersRepository followRepo;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private com.example.Data.Configuration.JWTService serve;

    private BCryptPasswordEncoder encode = new BCryptPasswordEncoder(12);

    public Iterable<User> findall() {
        return userRepo.findAll();
    }

    public String verify(User user) {
        org.springframework.security.core.Authentication authenticate =
            manager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );

        if (authenticate.isAuthenticated()) {
            return serve.generateToken(user.getUserName());
        }
        return "failure";
    }

    public void addUser(String email, String name, String password, String user_name, MultipartFile profile) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        String imageUrl = service.uploadFile(profile);

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setUserName(user_name);
        user.setPassword(encode.encode(password));
        user.setProfile(imageUrl);
        userRepo.save(user);
    }

    public Optional<User> getUser(@PathVariable long id) {
        return userRepo.findById(id);
    }

    public UserService(UserRepository userRepository, FollowersRepository followersRepository) {
        this.userRepo = userRepository;
        this.followRepo = followersRepository;
    }

    @Transactional
    public void followUser(Long followerId, Long followingId) {
        if (followRepo.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new RuntimeException("Already following this user");
        }

        Optional<User> followerOpt = userRepo.findById(followerId);
        Optional<User> followingOpt = userRepo.findById(followingId);

        if (followerOpt.isPresent() && followingOpt.isPresent()) {
            Followers follow = new Followers();
            follow.setFollower(followerOpt.get());
            follow.setFollowing(followingOpt.get());
            followRepo.save(follow);
        }
    }

    @Transactional
    public void unfollowUser(Long followerId, Long followingId) {
        followRepo.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }

    public List<Followers> findByUserId(long id) {
        return followRepo.findByFollowingId(id);
    }

    public int howMuch(Long id) {
        return followRepo.countByFollowingId(id);
    }

    public int howMuchf(Long id) {
        return followRepo.countByFollowerId(id);
    }
    public List<Followers> findByUserfId(long id) {
        return followRepo.findByFollowerId(id);
    }

}
