package com.bugcatcher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bugcatcher.model.ImageFileData;

@Repository
public interface ImageFileDataRepository extends JpaRepository<ImageFileData, Long>{
	
	@Query("SELECT i FROM ImageFileData i WHERE i.fileName = :filename")
	ImageFileData findByFileName(@Param("filename") String fileName);
	
	@Query("SELECT DISTINCT i FROM ImageFileData i WHERE i.fileName = :filename and i.ticketId = :ticketId")
	ImageFileData findDistinctByFileNameAndTicketId(@Param("filename") String filename, @Param("ticketId") Long ticketId);

}
