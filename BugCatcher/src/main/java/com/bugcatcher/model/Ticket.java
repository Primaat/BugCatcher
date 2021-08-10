package com.bugcatcher.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@SequenceGenerator(name="TICKET_SEQ", sequenceName="ticket_sequence")
@Table(name = "TICKET")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "TICKET_SEQ")
	private Long id;
	
	// DB relationship of tickets made by a User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	private String submitter;
	
	private String modifierName;
	
	// DB relationship of tickets made on a project
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;
	
	private String projectName;
	
	@ElementCollection(targetClass=Long.class)
	private List<Long> assignedUserIds = new ArrayList<>();
	
	@CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")	
	private ZonedDateTime creationTimeStamp = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("CET"));
	
	@LastModifiedDate
    @Column(name = "modified_at")
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")	
	private ZonedDateTime modifiedTimeStamp;
	
	@Column(name = "title", nullable = false)
	@NotNull(message="Please enter a title")
	private String title;
	
	@Size(min= 10, max=400, message="Please enter a description of 10 to 400 characters long ")
	private String description;	
	
	@ElementCollection(targetClass=String.class)
	private List<String> prioritiesList = new ArrayList<String>(Arrays.asList(new String[] {"Low", "Medium", "High"}));
	
	@Column(name = "ticketPriority")
	private String ticketPriority = prioritiesList.get(0);	
	
	@Column(name = "ticketStatus")
	private String ticketStatus = "Open";	
	
	@ElementCollection(targetClass=String.class)
	private List<String> statusList = new ArrayList<String>(Arrays.asList(new String[] {"Open", "In Progress", "Resolved"}));
	
	@Column(name = "ticketType")
	private String ticketType;	
	
	@ElementCollection(targetClass=String.class)
	private List<String> typeList = new ArrayList<String>(Arrays.asList(new String[] {"Bugs/Errors", "Feature Request", "Documentation Request", "Other"}));
	
	@Column(name = "action")
	private String action;	
	
	@ElementCollection(targetClass=String.class)
	private List<String> actionList = new ArrayList<String>(Arrays.asList(new String[] {"Assigned User", "Unassigned user", "Modified ticket"}));
	
	public Ticket() {}	
	/*
	 * Constructor used by devs, admins and project Managers
	 */			
	public Ticket(String title, String description, String ticketPriority, String ticketType, String ticketCreator, String projectName) {
		this.title = title;
		this.description = description;
		this.ticketPriority = ticketPriority;
		this.ticketType = ticketType;
		this.projectName = projectName;
		this.setCreationTimeStamp();
	}
	
	public Ticket(String title, String description, String ticketPriority, String ticketType, String submitter) {
		this.title = title;
		this.description = description;
		this.ticketPriority = ticketPriority;
		this.ticketType = ticketType;
		this.submitter = submitter;
		this.setCreationTimeStamp();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	

	public String getSubmitter() {
		return submitter;
	}
	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}
	
	public String getModifierName() {
		return modifierName;
	}
	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<Long> getAssignedUserIds() {
		return assignedUserIds;
	}
	
	public void addAssignedUserIds(Long userId) {
		this.assignedUserIds.add(userId);
	}
	
	public void setAssignedUserIds(List<Long> assignedUserIds) {
		this.assignedUserIds = assignedUserIds;
	}
	
	
	public ZonedDateTime getCreationTimeStamp() {
		return creationTimeStamp;
	}	

	public void setCreationTimeStamp() {
		this.creationTimeStamp = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("CET"));
	}
	
	public ZonedDateTime getModifiedTimeStamp() {
		return modifiedTimeStamp;
	}	
	
	public void setModifiedTimeStamp() {
		this.modifiedTimeStamp = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("CET"));
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTicketPriority() {
		return ticketPriority;
	}

	public void setTicketPriority(String ticketPriority) {
		this.ticketPriority = ticketPriority;
	}	

	public List<String> getPrioritiesList() {
		return prioritiesList;
	}
	
	public void setPrioritiesList(List<String> prioritiesList) {
		this.prioritiesList = prioritiesList;
	}
	
	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}	

	public List<String> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}	

	public List<String> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<String> getActionList() {
		return actionList;
	}
	public void setActionList(List<String> actionList) {
		this.actionList = actionList;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationTimeStamp == null) ? 0 : creationTimeStamp.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modifiedTimeStamp == null) ? 0 : modifiedTimeStamp.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((submitter == null) ? 0 : submitter.hashCode());
		result = prime * result + ((ticketPriority == null) ? 0 : ticketPriority.hashCode());
		result = prime * result + ((ticketStatus == null) ? 0 : ticketStatus.hashCode());
		result = prime * result + ((ticketType == null) ? 0 : ticketType.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Ticket other = (Ticket) obj;
		if (creationTimeStamp == null) {
			if (other.creationTimeStamp != null)
				return false;
		} else if (!creationTimeStamp.equals(other.creationTimeStamp))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modifiedTimeStamp == null) {
			if (other.modifiedTimeStamp != null)
				return false;
		} else if (!modifiedTimeStamp.equals(other.modifiedTimeStamp))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (submitter == null) {
			if (other.submitter != null)
				return false;
		} else if (!submitter.equals(other.submitter))
			return false;
		if (ticketPriority == null) {
			if (other.ticketPriority != null)
				return false;
		} else if (!ticketPriority.equals(other.ticketPriority))
			return false;
		if (ticketStatus == null) {
			if (other.ticketStatus != null)
				return false;
		} else if (!ticketStatus.equals(other.ticketStatus))
			return false;
		if (ticketType == null) {
			if (other.ticketType != null)
				return false;
		} else if (!ticketType.equals(other.ticketType))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", user=" + user + ", submitter=" + submitter + ", modifierName=" + modifierName
				+ ", projectName=" + projectName + ", creationTimeStamp=" + creationTimeStamp + ", modifiedTimeStamp="
				+ modifiedTimeStamp + ", title=" + title + ", description=" + description + ", ticketPriority="
				+ ticketPriority + ", ticketStatus=" + ticketStatus + ", ticketType=" + ticketType + "]";
	}
}
