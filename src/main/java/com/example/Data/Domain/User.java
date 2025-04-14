package com.example.Data.Domain;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class User {
  
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true , nullable=false)
	private String userName;
	
	
	private String name;
	
	@Column(unique=true )
	private String email;
	 @Lob
     private String profile;
	 @Column(unique=true , nullable=false)
     private String password;
	
	private LocalDateTime createdAt;
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL ,mappedBy="user")
	private List<Post> post;
	
	@JsonIgnore
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Followers> following; // List of users this user follows
    
	@JsonIgnore
    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Followers> followers; // List of users following this user


	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}





	
	public Set<Followers> getFollowing() {
		return following;
	}

	public void setFollowing(Set<Followers> following) {
		this.following = following;
	}

	public Set<Followers> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<Followers> followers) {
		this.followers = followers;
	}

	public User(Long id, String userName, String name, String email, String profile, String password,
			LocalDateTime createdAt, List<Post> post) {
		super();
		this.id = id;
		this.userName = userName;
		this.name = name;
		this.email = email;
		this.profile = profile;
		this.password = password;
		this.createdAt = createdAt;
		this.post = post;
	}

	public List<Post> getPost() {
		return post;
	}

	public void setPost(List<Post> post) {
		this.post =post;
	}

	



	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", name=" + name + ", email=" + email + ", profile="
				+ profile + ", password=" + password + ", createdAt=" + createdAt + ", post=" + post + "]";
	}

	public User() {
		
	}
}
