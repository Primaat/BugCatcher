//package com.bugcatcher.repository;
//
//import java.util.List;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//
//import com.bugcatcher.message.Message;
//import com.bugcatcher.model.Ticket;
//
//public interface MessageRepository extends CrudRepository<Message, Long>{
////	
////	@Query("select m from Message m where lower(m.to) like lower(concat('%', :search, '%')) ")
////    List<Message> findByTo(@Param("search") String search);
//
//}