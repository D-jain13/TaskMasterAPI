package com.dhairya.taskMaster.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dhairya.taskMaster.DTOs.TeamLeadDTO;
import com.dhairya.taskMaster.entity.Developer;
import com.dhairya.taskMaster.entity.Project;
import com.dhairya.taskMaster.entity.ProjectManager;
import com.dhairya.taskMaster.entity.Task;
import com.dhairya.taskMaster.entity.TeamLead;
import com.dhairya.taskMaster.enums.Role;
import com.dhairya.taskMaster.exception.ResourceAlreadyExistsException;
import com.dhairya.taskMaster.exception.ResourceNotFoundException;
import com.dhairya.taskMaster.repository.ProjectManagerRepo;
import com.dhairya.taskMaster.repository.TeamLeadRepo;
@Service
public class TeamLeadService {

	@Autowired
	TeamLeadRepo teamLeadRepo;
	
	@Autowired
	ProjectManagerRepo projectManagerRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	public Page<TeamLead> getAllTeamLead(Pageable pageable) {
		 
		return teamLeadRepo.findAll(pageable);
	}

	public TeamLead getByIdTeamLead(String id) {
		 
		TeamLead tl = teamLeadRepo.findById(id).orElseThrow( () -> new ResourceNotFoundException("Team Lead", "Id", id));
		return tl;
	}

	public String createTeamLead(TeamLeadDTO teamLeadDTO) {
		 
		Optional<TeamLead> tl = teamLeadRepo.findByEmail(teamLeadDTO.getEmail());

		if (tl.isPresent()) {
			throw new ResourceAlreadyExistsException("Project Manager", "Email", tl.get().getEmail());
		} else {
			tl = Optional.of(convertDtoToTl(teamLeadDTO));
			teamLeadRepo.save(tl.get());
			return "Team Lead Created Successfully";
		}
		
	}

	private TeamLead convertDtoToTl(TeamLeadDTO teamLeadDTO) {
		 
		TeamLead new_tl = new TeamLead();
		
		new_tl.setFull_name(teamLeadDTO.getFull_name());
		new_tl.setEmail(teamLeadDTO.getEmail());
		new_tl.setPassword(encoder.encode(teamLeadDTO.getPassword()));
		new_tl.setDepartment(teamLeadDTO.getDepartment());
		new_tl.setCreatedDate(LocalDateTime.now());
		new_tl.setLastUpdatedAt(LocalDateTime.now());
		new_tl.setTaskAssignedBy(new HashSet<Task>());
		new_tl.setRole(Role.TL);
		new_tl.setTaskAssignedTo(new HashSet<Task>());
		
		ProjectManager pm = projectManagerRepo.findById(teamLeadDTO.getProject_manager_id()).orElseThrow(() -> new ResourceNotFoundException("Project Manager", "Id", teamLeadDTO.getProject_manager_id()));
		new_tl.setProjectManager(pm);
		
		new_tl.setDevelopers(new HashSet<Developer>());
		new_tl.setProjects(new HashSet<Project>());
		
		return new_tl;
	}

	public String updateTeamLead(String id, TeamLeadDTO teamLeadDTO) {
		 
		TeamLead tl = teamLeadRepo.findById(id).orElseThrow( () -> new ResourceNotFoundException("Team Lead", "Id", id));
		
		tl.setFull_name(teamLeadDTO.getFull_name());
		tl.setEmail(teamLeadDTO.getEmail());
		tl.setPassword(encoder.encode(teamLeadDTO.getPassword()));
		tl.setLastUpdatedAt(LocalDateTime.now());
		tl.setDepartment(teamLeadDTO.getDepartment());
		
		ProjectManager pm = projectManagerRepo.findById(teamLeadDTO.getProject_manager_id()).orElseThrow(() -> new ResourceNotFoundException("Project Manager", "Id", teamLeadDTO.getProject_manager_id()));
		tl.setProjectManager(pm);
		
		teamLeadRepo.save(tl);
		return "Team Lead is updated";
		
	}

	public String deleteById(String id) {
		 
		TeamLead tl = teamLeadRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("TeamLead", "Id", id));
		
		teamLeadRepo.deleteById(id);
		return "Team Lead is deleted successfully";
	}


}
