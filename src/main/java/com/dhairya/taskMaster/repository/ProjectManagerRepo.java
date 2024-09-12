package com.dhairya.taskMaster.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhairya.taskMaster.entity.ProjectManager;

@Repository
public interface ProjectManagerRepo extends JpaRepository<ProjectManager, String> {

	Optional<ProjectManager> findByEmail(String email);

}
