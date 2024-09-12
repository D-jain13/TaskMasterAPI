package com.dhairya.taskMaster.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhairya.taskMaster.entity.HeadOfDepartment;

@Repository
public interface HODRepo extends JpaRepository<HeadOfDepartment, String> {

	Optional<HeadOfDepartment> findByEmail(String email);
}
