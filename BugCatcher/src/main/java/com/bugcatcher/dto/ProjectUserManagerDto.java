package com.bugcatcher.dto;

import java.util.ArrayList;
import java.util.List;

import com.bugcatcher.model.User;

public class ProjectUserManagerDto {
	
	private List<User> users = new ArrayList<>();	

	public ProjectUserManagerDto() {
	}

	public ProjectUserManagerDto(List<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
        this.users.add(user);
    }

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
