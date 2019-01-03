package com.mspaulo.imageloader.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mspaulo.imageloader.model.User;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
	
	User findOneById(String id);
	User findOneByUsername(String username);
	
	List<User> findByUsername(String username);
	
}