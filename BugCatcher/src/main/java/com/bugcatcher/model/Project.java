package com.bugcatcher.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="PROJECT")
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String projectName;
	private String projectDescription;
	
	// DB relationship of Users assigned to this Project
	@ManyToMany(mappedBy="assignedProjects", fetch = FetchType.EAGER)
	private List<User> userList = new ArrayList<User>();
	
	// DB Relationship of Tickets made for this Project
	@OneToMany(mappedBy="project", fetch = FetchType.EAGER)
	private List<Ticket> ticketList = new ArrayList<Ticket>();	
	
	public Project() {}		

	public Project(String projectName, String projectDescription) {
		this.projectName = projectName;
		this.projectDescription = projectDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void addUserToList(User user) {
		this.userList.add(user);
	}
	
	public void removeUserFromList(User user) {
		this.userList.remove(user);
	}

	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void addTicketToList(Ticket ticket) {
		this.ticketList.add(ticket);
	}
	
	public void removeTicketFromList(Ticket ticket) {
		this.userList.remove(ticket);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", projectName=" + projectName + ", projectDescription=" + projectDescription
				+ "]";
	}

}
