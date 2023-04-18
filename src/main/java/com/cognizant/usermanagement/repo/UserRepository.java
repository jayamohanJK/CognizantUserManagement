package com.cognizant.usermanagement.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognizant.usermanagement.model.User;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer>
{
	
	@Query(value ="Select u from User u where u.username= :uname and u.password = :pword")
	public User validateUser(String uname, String pword);
	
	boolean existsByUsername(String str);

}
