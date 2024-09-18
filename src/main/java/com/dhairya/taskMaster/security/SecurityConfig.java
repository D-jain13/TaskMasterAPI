package com.dhairya.taskMaster.security;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtFilter;
	
		
	 @Bean
	 UserDetailsService userDetailsService() {
		return new UserDetailServiceImpl(); 
	 }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		provider.setUserDetailsService(userDetailsService());

		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();

	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(request -> request
				// .requestMatchers(HttpMethod.POST,"/developer/create").permitAll()
//				.requestMatchers("/hod/**").hasRole("HOD").requestMatchers("/pm/**").hasAnyRole("PM", "HOD")
//				.requestMatchers("/tl/**").hasAnyRole("PM", "TL", "HOD").requestMatchers("/dev/**")
//				.hasAnyRole("HOD", "DEV", "PM", "TL")
				.requestMatchers("/login").permitAll()
				.requestMatchers("/refreshToken").permitAll()
				.requestMatchers("/developer/getMyTasks/**").hasRole("DEV")
				// .requestMatchers("/api/login","/api/logout").permitAll()
				.anyRequest().authenticated()
		// .anyRequest().permitAll()
		)

				.httpBasic(Customizer.withDefaults())
				.logout(logout -> logout.logoutUrl("/logout")
										.permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(cors -> cors.disable())
				.csrf(csrf -> csrf.disable())
				.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
	

		return http.build();
	}
}


