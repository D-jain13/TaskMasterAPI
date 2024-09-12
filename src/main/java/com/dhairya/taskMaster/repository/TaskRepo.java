package com.dhairya.taskMaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhairya.taskMaster.entity.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task, String> {

	
}
