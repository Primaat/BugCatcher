package com.bugcatcher.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.bugcatcher.exception.StorageFileNotFoundException;
import com.bugcatcher.exception.TicketNotFoundException;
import com.bugcatcher.model.Comment;
import com.bugcatcher.model.CustomUserDetails;
import com.bugcatcher.model.ImageFileData;
import com.bugcatcher.model.HistoryTicket;
import com.bugcatcher.model.Project;
import com.bugcatcher.model.Ticket;
import com.bugcatcher.model.User;
import com.bugcatcher.repository.ProjectRepository;
import com.bugcatcher.repository.CommentRepository;
import com.bugcatcher.repository.HistoryTicketRepository;
import com.bugcatcher.repository.ImageFileDataRepository;
import com.bugcatcher.repository.TicketRepository;
import com.bugcatcher.repository.UserRepository;
import com.bugcatcher.service.StorageService;

@Transactional
@Controller
public class TicketController {	
	
	@Autowired private TicketRepository ticketRepository;	
	@Autowired private HistoryTicketRepository historyTicketRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private ProjectRepository projectRepository;
	@Autowired private CommentRepository commentRepository;
	@Autowired private StorageService storageService;
	@Autowired private ImageFileDataRepository imageFileDataRepository;
	private String[] actions = new String[] {"Assigned User", "Unassigned user", "Modified ticket"};
	
	//Get all tickets from the DB
	@GetMapping("/tickets")
	public String getTickets(Model model) {
		
		model.addAttribute("tickets", ticketRepository.findAll());
		
		return "tickets/tickets";
	}	
	
	// Get a single ticket by ID
	@GetMapping("/ticket/{ticketId}")
	public String getTicket(Model model, @PathVariable("ticketId") Long ticketId, Authentication authentication) {
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User currentUser = userRepository.findByEmail(userDetails.getUsername());
		
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(()-> new TicketNotFoundException(ticketId));
		
		List<HistoryTicket> history = historyTicketRepository.findAllHistoryTicketsByTicketIdNum(ticket.getId());	
		List<Comment> comments = commentRepository.findAllCommentsByTicketIdNum(ticket.getId());	
		
		List<User> users = userRepository.findAll();
		List<User> assignedUsers = new ArrayList<>();
		
		for(Long i: ticket.getAssignedUserIds()) {
			for(User user: users) {
				if(user.getId() == i) assignedUsers.add(user);
			}
		}
		
		Comment comment = new Comment();		
		comment.setCreator(currentUser.getFullName());
		comment.setUserIdNum(currentUser.getId());
		comment.setTicketIdNum(ticketId);		
		 
		model.addAttribute("assigned", assignedUsers);
		model.addAttribute("history", history);
		model.addAttribute("ticket", ticket);
		model.addAttribute("tickets", ticket);
		model.addAttribute("comment", comment);
		model.addAttribute("comments", comments);
		
		List<String> paths = storageService.loadAll().parallel().map(
				path -> MvcUriComponentsBuilder.fromMethodName(TicketController.class,
				"serveFile", path.getFileName().toString()).build().toUri().toString())
		.collect(Collectors.toList());
		
		storageService.loadAll().iterator().forEachRemaining(System.out::println);

		List<Path> pathNames = storageService.loadAll().collect(Collectors.toList()).stream().parallel().map(Path::getFileName).collect(Collectors.toList());
		
		Map<ImageFileData, String> imagesAndNames = new HashMap<>();
		ImageFileData imageFileData = null;
		String pathname = "";
		
		for(int i = 0; i < paths.size(); i++ ) {			
			String rawPath = pathNames.get(i).toString();
			pathname = rawPath.substring(rawPath.lastIndexOf("--") + 2);
			imageFileData = imageFileDataRepository.findDistinctByFileNameAndTicketId(rawPath, ticketId);
			imagesAndNames.put(imageFileData, pathname);
		}		
		imagesAndNames.remove(null);
		model.addAttribute("imageFiles", imagesAndNames);

		ImageFileData imageFile = new ImageFileData();
		imageFile.setTicketId(ticketId);	
		
		model.addAttribute("imageFile", imageFile);
		
		return "tickets/ticket";		
	}
	
	//For downloading files
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws Exception {		

		Resource file = storageService.loadAsResource(filename);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + filename + "\"").body(file);
	}
	
	//uploading files
	@RequestMapping(value="/upload/{ticketId}", method = RequestMethod.POST)
	public String handleFileUpload(@PathVariable("ticketId") Long ticketId, @Validated ImageFileData imageFile, 
			@RequestParam("file") MultipartFile file, @RequestParam("description") String description,
			Authentication authentication) throws IOException {
		
		String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()) + "--" + file.getOriginalFilename();		
		storageService.store(file, newFileName);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userRepository.findByEmail(userDetails.getUsername());		
		
		imageFile.setUploader(user.getFullName());
		imageFile.setTicketId(ticketId);		
		imageFile.setFileName(newFileName);
		
		Path path = storageService.load(newFileName);
		String pathString = MvcUriComponentsBuilder.fromMethodName(TicketController.class,
				"serveFile", path.getFileName().toString()).build().toUri().toString();		
		
		imageFile.setPath(pathString);		
		
		imageFileDataRepository.save(imageFile);

		return "redirect:/ticket/{ticketId}";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
	
	//Add a comment to the current ticket
	@PostMapping("/addComment/{ticketId}")									
	public String addComment( @PathVariable("ticketId") Long ticketId,
			@Validated Comment comment, 
			BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			return "redirect:/ticket/{ticketId}";
		}
			
		commentRepository.save(comment);
		return "redirect:/ticket/{ticketId}";
	}
	
	// Getmap to modify a ticket
	@GetMapping("/modify/{id}")
	public String getModifiableTicket(@PathVariable Long id, Model model, Authentication authentication) {
		
		Ticket ticket = ticketRepository.findById(id).orElseThrow(()-> new TicketNotFoundException(id));
		
		model.addAttribute("ticket", ticket);
		
		if(ticket.getTicketStatus() == "Resolved") return "tickets/resolved";
		
		return "tickets/edit-ticket";		
	}

	
	//Postmap to update existing ticket 
	@PostMapping("/update/{id}")													
	public String updateTicket(@PathVariable("id") Long id, @Validated Ticket ticket, Authentication authentication, BindingResult result, Model model ) {
		
		if(result.hasErrors()) {
			return "tickets/edit-ticket";
		}
		
		System.out.println("Assigned user ids: ");
		System.out.println(ticket.getAssignedUserIds());
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userRepository.findByEmail(userDetails.getUsername());
			
		HistoryTicket historyTicket = new HistoryTicket(ticket.getId(), actions[2], user.getFullName());
		historyTicketRepository.save(historyTicket);
		ticket.setModifiedTimeStamp();
		
		ticketRepository.save(ticket);
		return "redirect:/ticket/{id}";
	}
	
	// Delete a ticket
	@RequestMapping(value="/delete/{id}", method= {RequestMethod.GET, RequestMethod.POST})
	public String deleteTicket(@PathVariable("id") Long id, Model model) {
		
		Ticket ticket = ticketRepository.findById(id).orElseThrow(()-> new TicketNotFoundException(id));
		
		ticketRepository.delete(ticket);
		
		return "redirect:/tickets"; 			
	}
	
	// Getmap to create a ticket form for General Users
	@GetMapping("/createTicket")
	public String createTicketFormGU(Model model) {	

		model.addAttribute("ticket", new Ticket());
		model.addAttribute("projects", projectRepository.findAll());
		
		return "tickets/createTicket";		
	}
	
	//postmap to process and show the submitted form for General Users
	@PostMapping("/createTicket")							
	public String createTicketGU(@Validated Ticket ticket, @RequestParam("file") MultipartFile file, Model model, Authentication authentication){
		
		User user = userRepository.findByEmail(authentication.getName());			
		String submitter = ((CustomUserDetails) authentication.getPrincipal()).getFullName();
		ticket.setSubmitter(submitter);
		
		ticketRepository.save(ticket);		
		
		String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()) + "--" + file.getOriginalFilename();
	
		storageService.store(file, newFileName);
		
		ImageFileData imageFile =  new ImageFileData();			
		imageFile.setTicketId(ticket.getId());
		imageFile.setUploader(submitter);
		imageFile.setFileName(newFileName);
			
		Path path = storageService.load(newFileName);
		String pathString = MvcUriComponentsBuilder.fromMethodName(TicketController.class,
				"serveFile", path.getFileName().toString()).build().toUri().toString();		
		
		imageFile.setPath(pathString);		
		
		imageFileDataRepository.save(imageFile);
		
		imageFileDataRepository.findAll().iterator().forEachRemaining(System.out::println);
		
		List<Project> projects = projectRepository.findByProjectNameOrProjectDescription(ticket.getProjectName());
		Project project = projects.get(0);		
		project.addTicketToList(ticket);		
		projectRepository.saveAndFlush(project);
		
		if(user.getRole() == "General_User"){
			return "tickets/ticketCreationSuccess";
		}

		return "redirect:/ticket/" + ticket.getId();

	}
	
//	@GetMapping("/process/{ticketId}/{projectId}")
//	public String processProjectTicketList(@PathVariable("ticketId") Long ticketId, @PathVariable("projectId") Long projectId,
//											HttpServletRequest request) {
//		
//		Project project = projectRepository.getOne(projectId);
//		Ticket ticket = ticketRepository.getOne(ticketId);
//		
//		project.addTicketToList(ticket);
//		
//		projectRepository.saveAndFlush(project);
//		
//		if(request.isUserInRole("ROLE_General_User")){
//			return "tickets/ticketCreationSuccess";
//		}
//		
//		System.out.println("User principal through HTTpservletrequest = " + request.getUserPrincipal()); 
//		
//		System.out.println("Ticket ID = " + ticket.getId());
//		project.getTicketList().forEach(System.out::println);
//		
//		return "redirect:/ticket/" + ticketId;
//	}
	
	/* TICKET STAT SECTION */
	
	// Get the amount of tickets by status, type and priority
	@GetMapping("/dashboard")
	public void getTicketStats(Model model) {
		
		model.addAttribute("urgent", ticketRepository.countAllTicketsByPriorityHigh());
		model.addAttribute("open", ticketRepository.countAllTicketsByStatusOpen());
		//model.addAttribute("new", ticketRepository.countAllTicketsByStatusOpen()); // need to see if day == today
		model.addAttribute("resolved", ticketRepository.countAllTicketsByStatusResolved());
		model.addAttribute("bugs", ticketRepository.countAllTicketsByTypeBugsAndErrors());
		model.addAttribute("documentation", ticketRepository.countAllTicketsByTypeDocumentationRequest());
		model.addAttribute("feature", ticketRepository.countAllTicketsByTypeFeatureRequest());
		model.addAttribute("other", ticketRepository.countAllTicketsByTypeOther());		 
	}
	
	@GetMapping("/open")
	public String showNewTickets(Model model) {
		
		model.addAttribute("tickets", ticketRepository.findAllTicketsByStatusOpen(null));
		
		return "tickets/tickets";
	}
	
	@GetMapping("/urgent")
	public String showUrgentTickets(Model model) {
		
		model.addAttribute("tickets", ticketRepository.findAllTicketsByPriorityHigh(null));
		
		return "tickets/tickets";
	}
	
	@GetMapping("/resolved")
	public String showresolvedTickets(Model model) {
		
		model.addAttribute("tickets", ticketRepository.findAllTicketsByStatusResolved(null));
		
		return "tickets/tickets";
	}
	
	@GetMapping("/bugs")
	public String showBugsTickets(Model model) {
		
		model.addAttribute("tickets", ticketRepository.findAllTicketsByTypeBugsAndErrors(null));
		
		return "tickets/tickets";
	}
	
	@GetMapping("/documentation")
	public String showDocumentationTickets(Model model) {
		
		model.addAttribute("tickets", ticketRepository.findAllTicketsByTypeDocumentationRequest(null));
		
		return "tickets/tickets";
	}
	
	@GetMapping("/feature")
	public String showFeatureTickets(Model model) {
		
		model.addAttribute("tickets", ticketRepository.findAllTicketsByTypeFeatureRequest(null));
		
		return "tickets/tickets";
	}
	
	@GetMapping("/other")
	public String showOtherTickets(Model model) {
		
		model.addAttribute("tickets", ticketRepository.findAllTicketsByTypeOther(null));
		
		return "tickets/tickets";
	}

}
