package com.dhairya.taskMaster.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dhairya.taskMaster.DTOs.AuthDTO;
import com.dhairya.taskMaster.DTOs.JwtResponse;
import com.dhairya.taskMaster.DTOs.RefreshTokenDTO;
import com.dhairya.taskMaster.entity.RefreshToken;
import com.dhairya.taskMaster.security.JwtService;
import com.dhairya.taskMaster.service.AuthService;
import com.dhairya.taskMaster.service.RefreshTokenService;


@RestController
public class AuhenticationController {

	@Autowired
	AuthService authService;
	
	@Autowired
	RefreshTokenService refreshTokenService;
	
	@Autowired
	JwtService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthDTO authDTO) {
		String accessToken = authService.verify(authDTO);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(authDTO.getEmail());
		JwtResponse jwtResponse = new JwtResponse(accessToken,refreshToken.getToken());
	//	JwtResponse jwtResponse = new JwtResponse(accessToken);
		return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(){
		return new ResponseEntity<>(authService.logout(),HttpStatus.OK);
	}
	
	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO){
			return refreshTokenService.findByToken(refreshTokenDTO.getToken())
	            .map(refreshTokenService::verifyExpiration)
	            .map(RefreshToken::getEmployee)
	            .map(employee -> {
	                String accessToken = jwtService.generateToken(employee.getEmail());
	                return new ResponseEntity<>(new JwtResponse(accessToken,refreshTokenDTO.getToken()),HttpStatus.OK);
	            }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
	}
}



