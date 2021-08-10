package com.bugcatcher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

	@RequestMapping("/dashboard")
	public String startDashboard() {	
		return "dashboard";
	}
	
	@RequestMapping("/assignRole")
	public String assignRoles() {
		return "users/assign-role";
	}
	
	@RequestMapping("/manageProjectUsers")
	public String assignRole() {
		return "users/project-users";
	}
	
	@RequestMapping("/profile")
	public String getProfile() {
		return "users/profile";
	}	

}
