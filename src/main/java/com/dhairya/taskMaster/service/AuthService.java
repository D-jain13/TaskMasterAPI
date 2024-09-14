package com.dhairya.taskMaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.dhairya.taskMaster.DTOs.AuthDTO;
import com.dhairya.taskMaster.repository.RefreshTokenRepo;
import com.dhairya.taskMaster.security.JwtService;

@Service
public class AuthService {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	RefreshTokenRepo refreshTokenRepo;
	
	public String verify(AuthDTO authDTO) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(),authDTO.getPassword()));
		
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(authDTO.getEmail());
		}
		else {
			return "fail";
		}
	}


	public String logout() {
	//	refreshTokenRepo.
		return null;
	}
}



