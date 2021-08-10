package com.bugcatcher.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@SequenceGenerator(name="COMMENT_SEQ", sequenceName="comment_sequence")
@Table(name="COMMENT")
public class Comment {
	
	@Id
	@Column(name="comment_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "COMMENT_SEQ")
	private Long id;
	
	// DB relationship of Comments made by a User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	// DB relationship of Comments made on a ticket
	@ManyToOne
	@JoinColumn(name="ticket_id")
	private Ticket ticket;	
	
	private Long ticketIdNum;
	
	private String creator;
	
	private Long userIdNum;
	
	@Size(min=10, max=100, message="Your comment must be between 10 and 100 chars long" )
	private String message;
	
	@CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")	
	private ZonedDateTime creationTimeStamp = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("CET"));
	
	public Comment() {}

	public Comment(User user, Ticket ticket, String message) {
		this.user = user;
		this.ticket = ticket;
		this.message = message;
	}			

	public Comment(Long ticketIdNum, Long userIdNum, String message) {
		this.ticketIdNum = ticketIdNum;
		this.userIdNum = userIdNum;
		this.message = message;
	}
	

	public Comment(Long id, Long ticketIdNum, String creator, Long userIdNum, String message) {
		this.ticketIdNum = ticketIdNum;
		this.creator = creator;
		this.userIdNum = userIdNum;
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTicketIdNum() {
		return ticketIdNum;
	}

	public void setTicketIdNum(Long ticketIdNum) {
		this.ticketIdNum = ticketIdNum;
	}	

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Long getUserIdNum() {
		return userIdNum;
	}

	public void setUserIdNum(Long userIdNum) {
		this.userIdNum = userIdNum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ZonedDateTime getCreationTimeStamp() {
		return creationTimeStamp;
	}

	public void setCreationTimeStamp(ZonedDateTime creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationTimeStamp == null) ? 0 : creationTimeStamp.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((userIdNum == null) ? 0 : userIdNum.hashCode());
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
		Comment other = (Comment) obj;
		if (creationTimeStamp == null) {
			if (other.creationTimeStamp != null)
				return false;
		} else if (!creationTimeStamp.equals(other.creationTimeStamp))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (userIdNum == null) {
			if (other.userIdNum != null)
				return false;
		} else if (!userIdNum.equals(other.userIdNum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", ticketIdNum=" + ticketIdNum + ", creator=" + creator + ", userIdNum="
				+ userIdNum + ", message=" + message + ", creationTimeStamp=" + creationTimeStamp + "]";
	}

}
