package com.bugcatcher.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bugcatcher.model.HistoryTicket;

public interface HistoryTicketRepository extends CrudRepository<HistoryTicket, Long>{

	@Query("SELECT h FROM HistoryTicket h WHERE h.ticketIdNum = :ticketIdNum")
	List<HistoryTicket> findAllHistoryTicketsByTicketIdNum(@Param("ticketIdNum") Long ticketIdNum);

}
