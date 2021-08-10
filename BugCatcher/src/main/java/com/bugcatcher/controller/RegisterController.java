package com.bugcatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.bugcatcher.model.User;
import com.bugcatcher.repository.UserRepository;

@Controller
public class RegisterController {
	
	@Autowired private UserRepository userRepository;		

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
	    
		model.addAttribute("user", new User());
	     
	    return "registration";
	}
	
	@PostMapping("/processRegistration")
	public String processRegister(User user, Model model) {
	    
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    user.setPassword(encodedPassword);	   
	    
	    User existingUser = userRepository.findByEmail(user.getEmailAddress());

	    if(existingUser != null) {
	    	model.addAttribute("existingUser", existingUser);
	    }
	    else {
	    	userRepository.save(user);
	    }
		   
	    return "registrationSuccess"; 	    
	}

}
