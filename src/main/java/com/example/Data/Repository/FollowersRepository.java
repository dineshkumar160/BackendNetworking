package com.example.Data.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Data.Domain.Followers;

public interface FollowersRepository extends JpaRepository<Followers, Long>{
	
	
	 void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);
	    
	    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);
	
	List<Followers> findByFollowingId(Long followingId);
	
	int countByFollowingId(Long followingId);

	int countByFollowerId(Long id);

	List<Followers> findByFollowerId(long id);

}
