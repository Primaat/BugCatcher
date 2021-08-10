package com.bugcatcher.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bugcatcher.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{
	
	@Query("SELECT t FROM Ticket t WHERE t.ticketPriority = 'High' and t.ticketStatus != 'Resolved' ORDER BY id")
	Page<Ticket> findAllTicketsByPriorityHigh(Pageable pageable);
	
	@Query("SELECT count(*) FROM Ticket t WHERE t.ticketPriority = 'High' and t.ticketStatus != 'Resolved'")
	Long countAllTicketsByPriorityHigh();
	
	@Query("SELECT t FROM Ticket t WHERE t.ticketStatus = 'Open' ORDER BY id")
	Page<Ticket> findAllTicketsByStatusOpen(Pageable pageable);
	
	@Query("SELECT count(*) FROM Ticket t WHERE t.ticketStatus = 'Open'")
	Long countAllTicketsByStatusOpen();
	
	@Query("SELECT t FROM Ticket t WHERE t.ticketStatus = 'In Progress' ORDER BY id")
	Page<Ticket> findAllTicketsByStatusInProgress(Pageable pageable);
	
	@Query("SELECT count(*) FROM Ticket t WHERE t.ticketStatus = 'In Progress'")
	Long countAllTicketsByStatusInProgress();
	
	@Query("SELECT t FROM Ticket t WHERE t.ticketStatus = 'Resolved' ORDER BY id")
	Page<Ticket> findAllTicketsByStatusResolved(Pageable pageable);
	
	@Query("SELECT count(*) FROM Ticket t WHERE t.ticketStatus = 'Resolved'")
	Long countAllTicketsByStatusResolved();
	
	@Query("SELECT t FROM Ticket t WHERE t.ticketType = 'Bugs/Errors' and t.ticketStatus != 'Resolved' ORDER BY id")
	Page<Ticket> findAllTicketsByTypeBugsAndErrors(Pageable pageable);
	
	@Query("SELECT count(*) FROM Ticket t WHERE t.ticketType = 'Bugs/Errors' and t.ticketStatus != 'Resolved'")
	Long countAllTicketsByTypeBugsAndErrors();
	
	@Query("SELECT t FROM Ticket t WHERE t.ticketType = 'Documentation Request' and t.ticketStatus != 'Resolved' ORDER BY id")
	Page<Ticket> findAllTicketsByTypeDocumentationRequest(Pageable pageable);
	
	@Query("SELECT count(*) FROM Ticket t WHERE t.ticketType = 'Documentation Request' and t.ticketStatus != 'Resolved'")
	Long countAllTicketsByTypeDocumentationRequest();
	
	@Query("SELECT t FROM Ticket t WHERE t.ticketType = 'Feature Request' and t.ticketStatus != 'Resolved' ORDER BY id")
	Page<Ticket> findAllTicketsByTypeFeatureRequest(Pageable pageable);
	
	@Query("SELECT count(*) FROM Ticket t WHERE t.ticketType = 'Feature Request' and t.ticketStatus != 'Resolved'")
	Long countAllTicketsByTypeFeatureRequest();
	
	@Query("SELECT t FROM Ticket t WHERE t.ticketType = 'Other' and t.ticketStatus != 'Resolved' ORDER BY id")
	Page<Ticket> findAllTicketsByTypeOther(Pageable pageable);
	
	@Query("SELECT count(*) FROM Ticket t WHERE t.ticketType = 'Other' and t.ticketStatus != 'Resolved'")
	Long countAllTicketsByTypeOther();
	
	@Query("select t from Ticket t where lower(t.title) like lower(concat('%', :search, '%')) " +
			"or lower(t.description) like lower(concat('%', :search, '%'))")
    List<Ticket> findByTitleOrDescription(@Param("search") String search);

}