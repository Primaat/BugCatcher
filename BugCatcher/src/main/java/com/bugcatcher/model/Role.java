package com.bugcatcher.model;

import java.util.Collection;

import javax.persistence.*;

@Entity
public class Role {
 
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    
    @OneToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
        name = "roles_privileges", 
        joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;
    
    public Role(String name) {
		this.name = name;
	}
}