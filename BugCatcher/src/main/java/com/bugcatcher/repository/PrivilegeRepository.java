package com.bugcatcher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bugcatcher.model.Privilege;

@Component
@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Long>{

	Privilege findByName(String name);
}
