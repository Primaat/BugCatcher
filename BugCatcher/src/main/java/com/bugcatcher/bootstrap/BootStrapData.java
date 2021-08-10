package com.bugcatcher.bootstrap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bugcatcher.model.Project;
import com.bugcatcher.model.Ticket;
import com.bugcatcher.model.User;
import com.bugcatcher.repository.ProjectRepository;
import com.bugcatcher.repository.TicketRepository;
import com.bugcatcher.repository.UserRepository;

@Component
public class BootStrapData implements CommandLineRunner{
	
	private final Random rand = new Random();
	//private final Roles[] roleValues = Roles.values();
	
	@Autowired private UserRepository userRepository2;
	@Autowired private ProjectRepository projectRepository;
	@Autowired private TicketRepository ticketRepository;

	@Override
	public void run(String... args) throws Exception {		
		createUsers();
		createProjects();		
		createTickets();					
	}		

	public void createUsers() {
		for(int i = 0; i < 18; i++) {
			userRepository2.save(getUser());
		}	
		userRepository2.findAll().forEach(System.out::println);
	}
	
	public void createTickets() {
		
		List<Project> projects = (List<Project>) projectRepository.findAll();
		
		for(int i = 0; i < 12; i++) {
			Project project = projects.get(rand.nextInt(7));	
			if((i != 10 && 1 != 6) && i%2 == 0) ticketRepository.save(new Ticket("Defect Printer", "The printer went down instead of up","Medium", "Other", "Johan Sap", project.getProjectName()));
			else if(i%3 == 0) ticketRepository.save(new Ticket("Windows are dirty", "I have a blue window of death","High", "Bugs/Errors", "Groenerik van den Rode", project.getProjectName() ));
			else if(i%5 == 0) ticketRepository.save(new Ticket("Apple is rotten", "My mac has a worm","High", "Bugs/Errors", "Joyce Toys", project.getProjectName()));
			else ticketRepository.save(new Ticket("Manual needed for Skype", "I need to use skype but don't know how it works","Low", "Documentation Request", "Henk Paap",project.getProjectName()));	
			
		}			
		ticketRepository.findAll().forEach(System.out::println);
	}
	
	private void createProjects() {
		String pName;
		
		List<String> words = new ArrayList<>(Arrays.asList(new String[] {"Apple","Windows","Link","Tech","Fixit","Connect","UNIX","Internet","Messaging","Email","Electro", "Universal","Printer"}));
		List<User> users = (List<User>) userRepository2.findAll();
		
		for(int i = 0; i < 7; i++ ) {
			pName = words.get(rand.nextInt(words.size())) + " " + words.get(rand.nextInt(words.size()));
			Project project = new Project(pName, "Project under construction");
			projectRepository.save(project);
			project.addUserToList(users.get(rand.nextInt(users.size())));
			project.addUserToList(users.get(rand.nextInt(users.size())));
			projectRepository.save(project);		
		}		
	}
	
	public String getRandomName() {
		
		String alphabet = "abcdefghijklmnopqrstuvwxyz";						
		int wordLen = rand.nextInt(6) + 4;
		
		StringBuilder name = new StringBuilder();
	
		for(int i = 0; i < wordLen; i++) {
			int letterSeed = rand.nextInt(alphabet.length());
			Character c = alphabet.charAt(letterSeed);
			if(i==0) {
				name.append(c.toUpperCase(c));
			}
			else {
				name.append(c);
			}
		}
		return name.toString();		
	}
	
	public String getEmailAddress() {
		String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789_-.";						
		int wordLen = rand.nextInt(6) + 4;
		
		StringBuilder name = new StringBuilder();
	
		for(int i = 0; i < wordLen; i++) {
			int letterSeed = rand.nextInt(alphabet.length());
			Character c = alphabet.charAt(letterSeed);
			if(i==0) {
				name.append(c.toUpperCase(c));
			}
			else {
				name.append(c);
			}
		}				
		return name.append("@ifixit.com").toString();			
	}
	
	public User getUser() {	
		return new User(getRandomName(), getRandomName(), getEmailAddress());
	}
}
