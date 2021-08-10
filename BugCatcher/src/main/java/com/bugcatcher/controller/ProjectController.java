package com.bugcatcher.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bugcatcher.converter.UserConverter;
import com.bugcatcher.dto.ProjectUserManagerDto;
import com.bugcatcher.exception.ProjectNotFoundException;
import com.bugcatcher.model.Project;
import com.bugcatcher.model.Ticket;
import com.bugcatcher.model.User;
import com.bugcatcher.repository.ProjectRepository;
import com.bugcatcher.repository.UserRepository;

@Transactional
@Controller
public class ProjectController {		
	
	@Autowired private ProjectRepository projectRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private UserConverter userConverter;
	
	@RequestMapping("/projects")
	public String getProjects(Model model) {
		
		model.addAttribute("projects", projectRepository.findAll());
		
		return "projects/projects";
	}
	
	@GetMapping("/project/{id}")
	public String getProject(@PathVariable Long id, Model model) {
		
		projectRepository.findAll().iterator().forEachRemaining(t -> t.getTicketList().stream().forEachOrdered(System.out::println));

		
		Project project = projectRepository.findById(id).orElseThrow(()->new ProjectNotFoundException(id));
		List<User> users = project.getUserList();
		List<Ticket> tickets = project.getTicketList();
		
		System.out.println("ticketList contains: " + project.getTicketList().toString());
		
		model.addAttribute("project", project);
		model.addAttribute("projectUsers", users);
		model.addAttribute("projectTickets", tickets);
		
		return "projects/project";		
	}
	
	// Ready new Project to add
	@GetMapping("/project/addProject")
	public String createNewProject(Project project) {
		
		return "projects/add-project";		
	}
	
	// Add the new Project
	@PostMapping("/project/addProject")
	public String addProject(@Valid Project project, BindingResult result, Model model ) {
		
		if(result.hasErrors()) {
			return "projects/add-project";
		}
		
		projectRepository.save(project);
		return "redirect:/projects";			
	}
	
	@GetMapping("/updateProject/{id}")
	public String getProjects(@PathVariable Long id, Model model) {
		
		Project project = projectRepository.findById(id).orElseThrow(()->new ProjectNotFoundException(id));
		
		model.addAttribute("project", project);
		
		return "projects/edit-project";
	}
	
	@PostMapping("/updateProject/{id}")
	public String updateProject(@PathVariable Long id, @Validated Project project, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			return "projects/project";
		}
		
		projectRepository.save(project);
		return "redirect:/projects";
	}
	
	@RequestMapping(value="/deleteProject/{id}", method= {RequestMethod.GET, RequestMethod.POST})
	public String deleteProject(@PathVariable Long id, Model model) {
		
		Project project = projectRepository.findById(id).orElseThrow(()->new ProjectNotFoundException(id));	

		projectRepository.delete(project);
		
		return "redirect:/projects";
	}	
	
	@GetMapping("/manageProjectUsers")
	public String getProjectUsers(Model model) {
		
		model.addAttribute("projects", projectRepository.findAll());
		
		return "projects/project-users";
	}
	
	
	@GetMapping("/manageProjectUsers/{projectId}")
	public String getProjectUsers(@PathVariable("projectId") Long projectId, Model model) {

		Project project = projectRepository.findById(projectId).orElseThrow(()->new RuntimeException("Project doesn't exist"));
		
		List<User> users = userRepository.findAll();
	
		List<User> assignedUsers = project.getUserList();
			
		List<User> unassignedUsers = new ArrayList<>();
		unassignedUsers.addAll(users);
		unassignedUsers.removeAll(assignedUsers);
	
		model.addAttribute("assignedDTO", new ProjectUserManagerDto(assignedUsers));
		model.addAttribute("unassignedDTO", new ProjectUserManagerDto(unassignedUsers));
		model.addAttribute("project", project);
		model.addAttribute("projects", projectRepository.findAll());
		model.addAttribute("projectName", project.getProjectName());
		model.addAttribute("projectId", projectId);
		
		return "projects/assign-project-users";
	}	
	
	@RequestMapping(value="/assignUser/{projectId}", method=  RequestMethod.POST)
	public String assignProjectUsers(@PathVariable("projectId") Long id, 			  
			@RequestParam("assignedUsers") List<String> users,  Model model) {	
		
		Project project = projectRepository.findById(id).get();
		User convUser;
		userConverter = new UserConverter(userRepository);
		
		List<String> assignedUsers = repairStringList(users);		
			
		List<User> convertedUsers = new ArrayList<>();
		for(String user: assignedUsers) {
			convUser = userConverter.convert(user);
			convertedUsers.add(convUser);
			convUser.addToAssignedProjects(project);
		}				
		userRepository.saveAll(convertedUsers);
		
		for(User user: convertedUsers) {
			project.addUserToList(user);
		}
		projectRepository.save(project);
		
		return "redirect:/manageProjectUsers/{projectId}";
	}

	@RequestMapping(value="/unAssignUser/{projectId}", method=  RequestMethod.POST)
	public String unAssignProjectUsers(@PathVariable("projectId") Long id, 			  
			@RequestParam("unAssignedUsers") List<String> users,  Model model) {	
		
		Project project = projectRepository.findById(id).get();
		User convUser;
		userConverter = new UserConverter(userRepository);		
		
		List<String> unAssignedUsers = repairStringList(users);
		
		List<User> convertedUsers = new ArrayList<>();
		for(String user: unAssignedUsers) {
			convUser = userConverter.convert(user);
			convertedUsers.add(convUser);
			convUser.removeFromAssignedProjects(project);
		}		
		userRepository.saveAll(convertedUsers);
		
		for(User user: convertedUsers) {
			project.removeUserFromList(user);
		}
		projectRepository.save(project);
		
		return "redirect:/manageProjectUsers/{projectId}";
	}
	
	// Method to repair a split string from consumed user list in the assign/unassign methods and return a list with a single string
	public List<String> repairStringList(List<String> users) {
		
		List<String> assignedUsers = users;
		String assignedUser = "";
		
		// check if the users list has been split and create a single string to use in the converter
		if(users.get(0).length() <=20) {
			for(String u : assignedUsers) {
				assignedUser += u + ", ";
			}
			if (assignedUser != null && assignedUser.length() > 0 && assignedUser.charAt(assignedUser.length() - 1) == ' ') {
				assignedUser = assignedUser.substring(0, assignedUser.length() - 2);
		    }
			assignedUsers.clear();
			assignedUsers.add(assignedUser);
			return assignedUsers;
		}	
		return users;		
	}
}
