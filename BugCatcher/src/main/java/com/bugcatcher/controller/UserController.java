package com.bugcatcher.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bugcatcher.exception.UserNotFoundException;
import com.bugcatcher.model.CustomUserDetails;
import com.bugcatcher.model.User;
import com.bugcatcher.repository.UserRepository;

@Controller
public class UserController {	
	
	@Autowired
	private UserDetailsService customUserDetailsService;
	
	@Autowired
	private UserRepository userRepository;

	// Get a list of all users
	@GetMapping("/users")
	public String getUsers(Model model) {
		
		model.addAttribute("users", userRepository.findAll());
		
		return "users/manage-users";
	}	
	
	// Get a single user
	@GetMapping("/user/{id}")
	public String getUser(Model model, @PathVariable Long id) {
		
		User user = userRepository.findById(id)
				.orElseThrow(()-> new UserNotFoundException(id));
		
		model.addAttribute("user", user);
		model.addAttribute("users", userRepository.findAll());
		
		return "users/manage-user";		
	}

	@GetMapping("/profile")
    public String currentUserDetails(Authentication authentication, Model model) {
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userRepository.findByEmail(userDetails.getUsername());		

		model.addAttribute("user", user);
		
		return "users/profile";		
    }
		
	// Ready new user to add
	@GetMapping("/user/addUser")
	public String createNewUser(User user) {
		
		return "users/add-user";		
	}
	
	// Add the new user
	@PostMapping("/user/addUser")
	public String addUser(@Valid User user, BindingResult result, Model model ) {
		
		if(result.hasErrors()) {
			return "users/add-user";
		}
		
		userRepository.save(user);
		return "redirect:/users";			
	}
	
	//Ready a user for updating
	@RequestMapping(value={"/user/update/{id}", "/profile/update/{id}"}, method = RequestMethod.GET)
	public String getUserForUpdating(@PathVariable("id") Long id, Model model, Authentication authentication) {
		
		User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
		
		model.addAttribute("user", user);
		model.addAttribute("users", userRepository.findAll());
		
		if(authentication.getName() == user.getEmailAddress()) {
			return "users/edit-profile";
		}
		
		return "users/update-user";
	}
	
	// Process the updated user
	@RequestMapping(value={"/user/update/{id}", "/profile/update/{id}"})
	public String updateUser(@Validated User user, @PathVariable("id") Long id, 
			BindingResult result, Model model, Authentication authentication) {
		
		if(result.hasErrors()) {			
			return "users/update-user";
		}
		
		userRepository.save(user);
		customUserDetailsService.loadUserByUsername(user.getEmailAddress()); 
		
		if(authentication.getName() == user.getEmailAddress()) {
			return "redirect:/profile";
		}
		
		return "redirect:/user/{id}";
	}
	
	// Process the updated current user
	@PostMapping("/profile/update/{id}")
	public String updateProfile(@Validated User user, @PathVariable("id") Long id, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			
			return "users/edit-profile";
		}
		
		userRepository.save(user);
		customUserDetailsService.loadUserByUsername(user.getEmailAddress()); 
		
		return "redirect:/profile";
	}
	
	//delete a user
	@RequestMapping(value="/user/delete/{id}", method= {RequestMethod.GET, RequestMethod.POST})
	public String deleteUser(@PathVariable("id") Long id, Model model, Authentication auth){
		
		User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));		
		userRepository.delete(user);
		
		return "redirect:/manageUser";
	}
	
	//Ready a user for updating a role
	@GetMapping("/manageUser")
	public String getUserForUpdating(Model model) {
		
		model.addAttribute("users", userRepository.findAll());
		
		return "users/manage-users";
	}	
	
	// Assign a role to any user
	@PostMapping("/manageUser/{id}")
	public String setUserRoles(@PathVariable("id") Long userId, @Validated User user, BindingResult result,  Model model) {		
		
		if(result.hasErrors()) {
			return "users/manage-users";
		}
	
		new CustomUserDetails(user);
		
		userRepository.save(user);
		
		return "redirect:/manageUser";
	}	

}
