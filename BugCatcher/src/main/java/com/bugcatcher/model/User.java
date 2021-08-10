package com.bugcatcher.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(name="USER_SEQ", sequenceName="user_sequence")
@Table(name="USER")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "USER_SEQ")	
	private Long id;
	
	@NotNull(message="Please enter a first name")
    @NotEmpty
	private String firstName;
	
	@NotNull(message="Please enter a last name")
	@NotEmpty
	private String lastName;
	
	private String fullName;
	
	//@NotNull(message="Please enter an email address")
	@Column(unique = true)
	private String emailAddress;	

	private String username;  	

	private String password;	
	
	private boolean enabled;
    private boolean tokenExpired;
    
    @Column(name = "reset_password_token")
    private String resetPasswordToken;
	
	//String[] roles = new String[]{"Administrator", "Project Manager", "Developer", "General User"};
	String[] roles = new String[]{"Administrator", "Project_Manager", "Developer", "General_User"};

	
	private String role = roles[3]; 
	
	// DB relationship of tickets made by this User
	@OneToMany(mappedBy = "user",  fetch = FetchType.EAGER)
	private Set<Ticket> ticketSet;
	
	// DB Relationship of Projects assigned to this User
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="assigned_projects", joinColumns= @JoinColumn(name="user_id"), 
				inverseJoinColumns= @JoinColumn(name="project_id"))
	private List<Project> assignedProjects;
	
	// DB Relationship of comments on tickets made by this User
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<Comment> ticketComments;
	
	public User() {}	
	
	public User(  String firstName, String lastName, String emailAddress,  String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}
	
	// Temporary constructor for debug only!!!!!
	public User(  String firstName, String lastName, String emailAddress,  String password, String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User(String firstName, String lastName, String emailAddress) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = firstName + " " + lastName;
		this.emailAddress = emailAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		setFullName();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		setFullName();
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName() {
		this.fullName = firstName + " " + lastName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}	
	
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}		

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}		

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public String[] getRoles() {
		return roles;
	}

	public void addRoleToArray(String role) {
		this.roles[role.length()] = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}	
	
	public Set<Ticket> getTicketSet() {
		return ticketSet;
	}

	public void addTicketToSet(Ticket ticket) {
		this.ticketSet.add(ticket);
	}

	public List<Project> getAssignedProjects() {
		return assignedProjects;
	}

	public void addToAssignedProjects(Project project) {
		this.assignedProjects.add(project);
	}
	
	public void removeFromAssignedProjects(Project project) {
		this.assignedProjects.remove(project);
	}

	public Set<Comment> getTicketComments() {
		return ticketComments;
	}

	public void addTicketComments(Comment comment) {
		this.ticketComments.add(comment);
	}		
	

//	public Set<Message> getReceivedMessages() {
//		return receivedMessages;
//	}
//
//	public void addReceivedMessage(Message message) {
//		this.receivedMessages.add(message);
//	}
//	
//	public Set<Message> getSentMessages() {
//		return sentMessages;
//	}
//
//	public void addSentMessages(Message message) {
//		this.sentMessages.add(message);
//	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", fullName=" + fullName
				+ ", emailAddress=" + emailAddress + ", role=" + role +  "]";
	}

}
