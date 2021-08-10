package com.bugcatcher.exception;

public class TicketNotFoundException extends RuntimeException{

	public TicketNotFoundException(Long id) {
		super("Ticket " + id + " doesn't exist");
	}
}
