package com.dhairya.taskMaster.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhairya.taskMaster.entity.Employee;
import com.dhairya.taskMaster.entity.RefreshToken;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {

	Optional<RefreshToken> findByToken(String token);

	Optional<Employee> findByEmployee(Employee employee);
}
