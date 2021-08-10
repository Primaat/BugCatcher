//package com.bugcatcher.message;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//
//import com.bugcatcher.model.User;
//
//@Entity
//@SequenceGenerator(name="MESSAGE_SEQ", sequenceName="message_sequence")
////@Table(name = "MESSAGES")
//public class Message {
//	  
//	@Id
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "MESSAGE_SEQ")	
//	private Long id;
//	
////	@ManyToOne
////	@JoinColumn(name="user_id")
////	private User user;
//
//  	private String from;
//  	private String to;
//  	private String body;
//
//  	public Message() {
//  	}
//
//  	public Message(String from, String to, String body) {
//  		this.from = from;
//  		this.to = to;
//  		this.body = body;
//  	} 
//
//  	public Long getId() {
//  		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getFrom() {
//		return from;
//	}
//
//	public void setFrom(String from) {
//		this.from = from;
//	}
//
//	public String getTo() {
//	    return to;
//	  }
//
//	  public void setTo(String to) {
//	    this.to = to;
//	  }
//
//	  public String getBody() {
//	    return body;
//	  }
//
//	  public void setBody(String body) {
//	    this.body = body;
//	  }
//
//	  @Override
//	  public String toString() {
//	    return String.format("Message{from=%s, to=%s, body=%s}",getFrom(), getTo(), getBody());
//	  }
//	
//}
