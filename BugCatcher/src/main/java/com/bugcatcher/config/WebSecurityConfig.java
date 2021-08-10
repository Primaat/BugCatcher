package com.bugcatcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bugcatcher.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
     
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
    
    @Bean
    public CustomUserDetailsService customUserDetailsService() {
		return new CustomUserDetailsService();    	
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());         
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/forgotPassword", "/resetPassword").permitAll()
	        .antMatchers("/", "/login").permitAll()
	        .antMatchers("/register","/processRegistration").permitAll()	        
	        .anyRequest().authenticated()
        .and()
        .formLogin()
	        .loginPage("/login")
	        .defaultSuccessUrl("/default")
	        .permitAll()
        .and()
        .logout()
	        .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
            ;
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/css/**");
            web.ignoring().antMatchers("/data/**");
            web.ignoring().antMatchers("/fonts/**");
            web.ignoring().antMatchers("/icons-reference/**");
            web.ignoring().antMatchers("/img/**");
            web.ignoring().antMatchers("/js/**");
            web.ignoring().antMatchers("/vendor/**");
    }
}
