package com.dhairya.taskMaster.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhairya.taskMaster.entity.RefreshToken;
import com.dhairya.taskMaster.exception.ResourceNotFoundException;
import com.dhairya.taskMaster.repository.EmpRepo;
import com.dhairya.taskMaster.repository.RefreshTokenRepo;

@Service
public class RefreshTokenService {

	@Autowired
	RefreshTokenRepo refreshTokenRepo;

	@Autowired
	EmpRepo empRepo;

	public RefreshToken createRefreshToken(String email) {
		RefreshToken refreshToken = RefreshToken.builder()
				.employee(empRepo.findByEmail(email)
						.orElseThrow(() -> new ResourceNotFoundException("Employee", "Email", email)))
				.token(UUID.randomUUID().toString()).expiryDate(Instant.now().plusMillis(30 * 60 * 1000)).build();
		return refreshTokenRepo.save(refreshToken);

	}


	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepo.findByToken(token);
	}
	
	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepo.delete(token);
			throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
		}
		return token;
	}


}
