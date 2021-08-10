package com.bugcatcher.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bugcatcher.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long>{

	@Query("SELECT c FROM Comment c WHERE c.ticketIdNum = :ticketIdNum")
	List<Comment> findAllCommentsByTicketIdNum(@Param("ticketIdNum") Long ticketIdNum);
	
	@Query("SELECT c FROM Comment c WHERE c.userIdNum = :userIdNum")
	List<Comment> findAllCommentsByUserIdNum(@Param("userIdNum") Long userIdNum);
}
