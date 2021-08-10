package com.bugcatcher.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@SequenceGenerator(name="HISTTICKET_SEQ", sequenceName="histticket_sequence")
@Table(name = "HISTORY_TICKET")
public class HistoryTicket {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "HISTTICKET_SEQ")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="ticket_id")
	private Ticket ticket;
	
	private Long ticketIdNum;

	private String action;
	
	private String modifierName;
	
	@CreatedDate
    @Column(name = "modified_at", nullable = false, updatable = false)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")	
	private ZonedDateTime modifiedTimeStamp = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("CET")); 
	

	public HistoryTicket() {
	}

	public HistoryTicket(Ticket ticket) {
		this.ticket = ticket;
	}	

	public HistoryTicket(Long ticketId, String action, String modifiedUser) {
		this.ticketIdNum = ticketId;
		this.action = action;
		this.modifierName = modifiedUser;
	}

	public Long getId() {
		return id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Long getTicketId() {
		return ticketIdNum;
	}

	public void setTicketId(Long ticketId) {
		this.ticketIdNum = ticketId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifiedUser) {
		this.modifierName = modifiedUser;
	}

	public ZonedDateTime getModifiedTimeStamp() {
		return modifiedTimeStamp;
	}

	public void setModifiedTimeStamp(ZonedDateTime creationTimeStamp) {
		this.modifiedTimeStamp = creationTimeStamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modifierName == null) ? 0 : modifierName.hashCode());
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
		HistoryTicket other = (HistoryTicket) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modifierName == null) {
			if (other.modifierName != null)
				return false;
		} else if (!modifierName.equals(other.modifierName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HistoryTicket [id=" + id + ", action=" + action + ", modifiedUser=" + modifierName
				+ ", modifiedTimeStamp=" + modifiedTimeStamp + "]";
	}	
}
