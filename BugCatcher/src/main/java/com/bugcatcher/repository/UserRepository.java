package com.bugcatcher.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bugcatcher.model.User;

@Component
@Repository
public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long>{
	
	List<User> findAll();
	
//	@Query("SELECT u FROM User u WHERE u.emailAddress = ?1")
//    boolean existsUserByEmailAddress(String emailAddress);
	
	@Query("SELECT u FROM User u WHERE u.emailAddress = ?1")
    User findByEmail(String emailAddress);
	
	@Query("SELECT u FROM User u WHERE u.resetPasswordToken = ?1")
    User findByResetPasswordToken(String resetPasswordToken);
	
	@Query("select u from User u where lower(u.emailAddress) like lower(concat('%', :search, '%'))")
    List<User> findByEmailAddress(@Param("search") String search);
	
	@Query("select u from User u where lower(u.firstName) like lower(concat('%', :search, '%')) " +
			"or lower(u.lastName) like lower(concat('%', :search, '%'))" + 
			"or lower(u.emailAddress) like lower(concat('%', :search, '%'))")
    List<User> findByFirstNameOrLastNameOrEmailAddress(@Param("search") String search);

}
