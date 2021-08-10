package com.bugcatcher.controller;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
	
	@Autowired
    private JavaMailSender mailSender;
     
    public void sendPlainEmail() {
    	String from = "jaap.jan.kees.gert@gmail.com";
    	String to = "jaap.jan.kees.gert@gmail.com";
    	 
    	SimpleMailMessage message = new SimpleMailMessage();
    	 
    	message.setFrom(from);
    	message.setTo(to);
    	message.setSubject("This is a plain text email");
    	message.setText("Hello guys! This is a plain text email.");
    	 
    	mailSender.send(message);
    }  
    
    public void sendHTMLEmail() throws MessagingException {
    	String from = "jaap.jan.kees.gert@gmail.com";
    	String to = "jaap.jan.kees.gert@gmail.com";
    	 
    	MimeMessage message = mailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message);
    	 
    	helper.setSubject("This is an HTML email");
    	helper.setFrom(from);
    	helper.setTo(to);
    	 
    	boolean html = true;
    	helper.setText("<b>Hey guys</b>,<br><i>Welcome to my new home</i>", html);
    	 
    	mailSender.send(message);
    } 
    
    public void sendAttachedEmail() throws MessagingException {
    	String from = "jaap.jan.kees.gert@gmail.com";
    	String to = "jaap.jan.kees.gert@gmail.com";
    	     
    	MimeMessage message = mailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message, true);
    	 
    	helper.setSubject("Here's your e-book");
    	helper.setFrom(from);
    	helper.setTo(to);
    	 
    	helper.setText("<b>Dear friend</b>,<br><i>Please find the book attached.</i>", true);
    	 
    	FileSystemResource file = new FileSystemResource(new File("Book.pdf"));
    	helper.addAttachment("FreelanceSuccess.pdf", file);
    	 
    	mailSender.send(message);
    } 
    
    public void sendInlineImageEmail() throws MessagingException {
    	String from = "jaap.jan.kees.gert@gmail.com";
    	String to = "jaap.jan.kees.gert@gmail.com";
    	     
    	MimeMessage message = mailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message, true);
    	 
    	helper.setSubject("Here's your pic");
    	helper.setFrom(from);
    	helper.setTo(to);
    	 
    	String content = "<b>Dear guru</b>,<br><i>Please look at this nice picture:.</i>"
    	        + "<br><img src='cid:image001'/><br><b>Best Regards</b>";
    	helper.setText(content, true);
    	 
    	FileSystemResource resource = new FileSystemResource(new File("picture.png"));
    	helper.addInline("image001", resource);
    	 
    	mailSender.send(message);
    } 
}
