package com.bugcatcher.exception;

public class ProjectNotFoundException extends RuntimeException {

	public ProjectNotFoundException(Long id) {
		super("Project " + id + " doesn't exist");
	}
}	
