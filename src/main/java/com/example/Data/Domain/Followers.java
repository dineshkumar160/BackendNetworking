package com.example.Data.Domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Followers {
	
	

	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "follower_id", nullable = false)
	    private User follower;  // The user who is following

	    @ManyToOne
	    @JoinColumn(name = "following_id", nullable = false)
	    private User following; // The user being followed

	    private LocalDateTime createdAt;  // Timestamp of follow action

	    @PrePersist
	    protected void onCreate() {
	        this.setCreatedAt(LocalDateTime.now());

}
	    

	    
	
		  public User getFollower() {
				return follower;
			}

			public void setFollower(User follower) {
				this.follower = follower;
			}

			public User getFollowing() {
				return following;
			}

			public void setFollowing(User following) {
				this.following = following;
			}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		public Followers(){
			
		}
		@Override
		public String toString() {
			return "Followers [id=" + id + ", follower=" + follower + ", following=" + following + ", createdAt="
					+ createdAt + "]";
		}

		public Followers( User follower, User following, LocalDateTime createdAt) {
			super();
	
			this.follower = follower;
			this.following = following;
			this.createdAt = createdAt;
		}
}
