package com.bugcatcher.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bugcatcher.model.Project;
import com.bugcatcher.model.Ticket;
import com.bugcatcher.model.User;
import com.bugcatcher.repository.ProjectRepository;
import com.bugcatcher.repository.TicketRepository;
import com.bugcatcher.repository.UserRepository;

@Controller
public class SearchController {
	
	@Autowired
	public UserRepository userRepository;
	@Autowired
	public TicketRepository ticketRepository;
	@Autowired
	public ProjectRepository projectRepository;
	
//	public SearchController(UserRepository userRepository, TicketRepository ticketRepository, ProjectRepository projectRepository ) {
//		this.userRepository = userRepository;
//		this.ticketRepository = ticketRepository;
//		this.projectRepository = projectRepository;
//		
//	}
	
	@RequestMapping(value="/search", method = RequestMethod.GET)
    public String searchDatabase(@RequestParam(name = "search") String search, Model model) {
        
		List<Ticket> tickets = ticketRepository.findByTitleOrDescription(search);
		
		List<Project> projects = projectRepository.findByProjectNameOrProjectDescription(search);
		
		List<User> users = userRepository.findByFirstNameOrLastNameOrEmailAddress(search);
		
		for(Ticket ticket:tickets) System.out.println(ticket.getId() + " " + ticket.getTitle() + " " + ticket.getDescription());
        
        model.addAttribute("ticketResults", tickets);
        model.addAttribute("projectResults", projects);
        model.addAttribute("userResults", users);
        
        return "search-results";
    }

}
