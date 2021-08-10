package com.bugcatcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bugcatcher.model.User;
import com.bugcatcher.repository.UserRepository;
import com.bugcatcher.model.CustomUserDetails;
 
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepo;
     
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        return new CustomUserDetails(user);
    }
    
    
    public UserDetails updateCustomUser(User user) throws UsernameNotFoundException {
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
//        List<Authority> authorityList = (List<Authority>) user.getAuthorities();
//        authorityList.clear();
//        authorityList.add(new Authority("ROLE_" + user.getRole()));
//
//        user.setAuthorities(authorityList);
        
        return new CustomUserDetails(user);
    }
    
    
 
}
