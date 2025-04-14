package com.example.Data.Repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Data.Domain.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

	User findByUserName(String userName);

	




	

}
