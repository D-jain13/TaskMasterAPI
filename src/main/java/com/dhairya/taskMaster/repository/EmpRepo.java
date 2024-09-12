package com.dhairya.taskMaster.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhairya.taskMaster.entity.Employee;

@Repository
public interface EmpRepo extends JpaRepository<Employee, String> {

	Optional<Employee> findByEmail(String email);
}
