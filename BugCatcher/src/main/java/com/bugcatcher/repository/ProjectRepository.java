package com.bugcatcher.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bugcatcher.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

	@Query("select p from Project p where lower(p.projectName) like lower(concat('%', :search, '%')) " +
			"or lower(p.projectDescription) like lower(concat('%', :search, '%'))")
    List<Project> findByProjectNameOrProjectDescription(@Param("search") String search);
}
