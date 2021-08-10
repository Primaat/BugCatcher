package com.bugcatcher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bugcatcher.model.Role;

@Component
@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	
	Role findByName(String string);
}
