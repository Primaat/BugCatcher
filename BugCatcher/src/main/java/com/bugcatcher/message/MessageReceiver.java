//package com.bugcatcher.message;
//
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Component;
//
//import com.bugcatcher.repository.UserRepository2;
//
//@Component
//public class MessageReceiver {
//	
//	private final UserRepository2 userRepository;	
//
//	public MessageReceiver(UserRepository2 userRepository) {
//		this.userRepository = userRepository;
//	}
//
//	@JmsListener(destination = "messagebox", containerFactory = "myFactory")
//	  public void receiveMessage(Message message) {
//	    System.out.println("Received <" + message + ">");	    
//	    
//	  }
//}
