package com.example.Data.Configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;







@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	@Autowired
	public MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain security(HttpSecurity http) throws Exception {
	return http.csrf(cus->cus.disable())
			.authorizeHttpRequests(req -> req
				    .requestMatchers("/aapi/register", "/aapi/login").permitAll()
				    .requestMatchers("/aapi/**").authenticated())

	   // .formLogin(Customizer.withDefaults())
	    
 .httpBasic(Customizer.withDefaults())
	   .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	   .cors() // Enable CORS support
       .and()
       .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
	     .build();
	  
		
	}
	
	

	@Bean
	public AuthenticationManager authmanager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new InMemoryUserDetailsManager();
//	}
	@Bean
	public AuthenticationProvider provider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
}
