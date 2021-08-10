package com.bugcatcher.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.bugcatcher.model.User;
import com.bugcatcher.repository.UserRepository;

@Component
public class UserConverter implements Converter<String, User> {

	private final UserRepository userRepository;	

	@Autowired
	public UserConverter(UserRepository userRepository) {
	    this.userRepository = userRepository;
	}

	@Override
	public User convert(String source) {

	      List<User> users = new ArrayList<>();

	      userRepository.findAll().forEach(i -> users.add(i));

	      for (User user : users) {

	        if (user.toString().equals(source))

	            return user;
	      }

	      return null;
	   }

	}
