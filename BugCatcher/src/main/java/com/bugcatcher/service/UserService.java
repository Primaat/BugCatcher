package com.bugcatcher.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bugcatcher.exception.UserNotFoundException;
import com.bugcatcher.model.User;
import com.bugcatcher.repository.UserRepository;

@Service
public class UserService {	
	
	private UserRepository userRepository;	
	
	public UserService(UserRepository userRepository2){
		this.userRepository = userRepository2;
	}
	
    public Page<User> findPaginated(Pageable pageable) {
    	
    	List<User> users = userRepository.findAll();
    	
        int pageSize = 10; 
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> list;

        if (users.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, users.size());
            list = users.subList(startItem, toIndex);
        }

        Page<User> userPage
          = new PageImpl<User>(list, PageRequest.of(currentPage, pageSize), users.size());

        return userPage;
    }
    
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
        	user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any customer with the email " + email);
        }
    }
     
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }
     
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
         
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }    
}
