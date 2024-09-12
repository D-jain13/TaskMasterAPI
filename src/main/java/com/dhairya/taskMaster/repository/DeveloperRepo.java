package com.dhairya.taskMaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhairya.taskMaster.entity.Developer;

@Repository
public interface DeveloperRepo extends JpaRepository<Developer, String>{

}
