package com.dhairya.taskMaster.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhairya.taskMaster.entity.TeamLead;

@Repository
public interface TeamLeadRepo extends JpaRepository<TeamLead, String>{

	Optional<TeamLead> findByEmail(String email);

}
