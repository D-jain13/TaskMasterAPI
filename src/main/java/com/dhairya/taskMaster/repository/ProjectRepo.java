package com.dhairya.taskMaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhairya.taskMaster.entity.Project;

@Repository
public interface ProjectRepo extends JpaRepository<Project, String> {

}
