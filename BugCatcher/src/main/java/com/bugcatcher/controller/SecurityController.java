package com.bugcatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bugcatcher.model.CustomUserDetails;
import com.bugcatcher.model.User;
import com.bugcatcher.repository.UserRepository;

@Controller
public class SecurityController {
	
	@Autowired private UserRepository userRepo;	

//	public SecurityController(UserRepository userRepo) {
//		this.userRepo = userRepo;
//	}
	
	@RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Authentication authentication) {
	
        return (String) authentication.getName();
    }
	
	@GetMapping("/userdetails")
    public void currentUserDetails(Authentication authentication, Model model) {
		
		User user = userRepo.findByEmail(authentication.getName());
		
		String fullName = ((CustomUserDetails) authentication.getPrincipal()).getFullName();
				
		System.out.println("USER DETAILS ARE: " + user.toString());
		System.out.println("Custom User Details Are: " + fullName);

		model.addAttribute("user", user);
		
    }

}
