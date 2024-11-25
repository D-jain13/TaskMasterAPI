package com.dhairya.taskMaster.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhairya.taskMaster.DTOs.DeveloperDTO;
import com.dhairya.taskMaster.entity.Developer;
import com.dhairya.taskMaster.entity.Project;
import com.dhairya.taskMaster.entity.Task;
import com.dhairya.taskMaster.entity.TeamLead;
import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.enums.Role;
import com.dhairya.taskMaster.enums.Status;
import com.dhairya.taskMaster.exception.ResourceNotFoundException;
import com.dhairya.taskMaster.repository.DeveloperRepo;
import com.dhairya.taskMaster.repository.ProjectRepo;
import com.dhairya.taskMaster.repository.TaskRepo;
import com.dhairya.taskMaster.repository.TeamLeadRepo;

@Service
public class DeveloperService {

	@Autowired
	DeveloperRepo developerRepo;

	@Autowired
	TeamLeadRepo teamLeadRepo;

	@Autowired
	ProjectRepo projectRepo;

	@Autowired
	TaskRepo taskRepo;

	@Autowired
	PasswordEncoder encoder;
	
	// To get list of all the developers
	public Page<Developer> getAllDevelopers(Pageable pageable) {
		return developerRepo.findAll(pageable);

	}

	// To get developer by specific id
	public Developer getById(String id) {
		return developerRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Developer", "Id", id));
	}

	// To get all the tasks assigned to a developer
	public Page<Task> getAllAssignedTasks(String id, Status status, Pageable pageable) {

		Developer dev = developerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Developer", "Id", id));

		List<Task> assignedTaskList = new ArrayList<Task>(dev.getTaskAssignedTo());

		if (status != null) {
			assignedTaskList.stream().filter(task -> task.getStatus().equals(status)).collect(Collectors.toList());
		}
		int start = (int)pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), assignedTaskList.size());
		
		if(start>end) {
			return new PageImpl<>(new ArrayList<>(),pageable,0);
		}
		 return new PageImpl<>(assignedTaskList.subList(start, end), pageable, assignedTaskList.size());

	}

	// To create a new developer
	@Transactional
	public String createDeveloper(DeveloperDTO developerDTO) {

		Developer dev = convertDtoToObject(developerDTO);
		developerRepo.save(dev);
		return "Developer created successfully";
	}

	// Method to convert DTO object to Developer object
	private Developer convertDtoToObject(DeveloperDTO developerDTO) {

		Developer new_dev = new Developer();

		new_dev.setFull_name(developerDTO.getFull_name());
		new_dev.setEmail(developerDTO.getEmail());
		new_dev.setPassword(encoder.encode(developerDTO.getPassword()));
		//new_dev.setPassword(developerDTO.getPassword());
		new_dev.setDepartment(developerDTO.getDepartment());
		new_dev.setTaskAssignedBy(new HashSet<Task>());
		new_dev.setTaskAssignedTo(new HashSet<Task>());
		new_dev.setSubDepartment(developerDTO.getSubDepartment());
		new_dev.setRole(Role.DEV);
		new_dev.setCreatedDate(LocalDateTime.now());
		new_dev.setLastUpdatedAt(LocalDateTime.now());

		TeamLead tl = teamLeadRepo.findById(developerDTO.getTeam_lead_id())
				.orElseThrow(() -> new ResourceNotFoundException("TeamLead", "Id", developerDTO.getTeam_lead_id()));
		new_dev.setTeamLead(tl);

		Set<Project> projects = new HashSet<>();
		try {
			projects = developerDTO.getProjects_id().stream().map(project_id -> {
				try {
					return projectRepo.findById(project_id)
							.orElseThrow(() -> new ResourceNotFoundException("Project", "project_id", project_id));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.toSet());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		projects.forEach(project -> project.getDevelopers().add(new_dev));
		new_dev.setAssignedProjects(projects);

		return new_dev;
	}

	// Update an existing developer
	@Transactional
	public String updateDeveloper(String id, DeveloperDTO developerDTO) {

		Developer existing_dev = developerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Developer", "Id", id));

		existing_dev.setFull_name(developerDTO.getFull_name());
		existing_dev.setEmail(developerDTO.getEmail());
		existing_dev.setPassword(encoder.encode(developerDTO.getPassword()));
		existing_dev.setDepartment(developerDTO.getDepartment());
		existing_dev.setSubDepartment(developerDTO.getSubDepartment());
		existing_dev.setLastUpdatedAt(LocalDateTime.now());

		developerRepo.save(existing_dev);

		return "Developer successfully updated";
	}

	// To update the department of the developer
	public String updateDepartment(String id, Department department) {

		Developer existing_dev = developerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Developer", "Id", id));

		existing_dev.setLastUpdatedAt(LocalDateTime.now());
		existing_dev.setDepartment(department);

		developerRepo.save(existing_dev);

		return "Developer's department successfully updated";
	}

	// To update the team lead of the developer
	public String updateTeamLead(String id, String team_lead_id) {

		Developer existing_dev = developerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Developer", "Id", id));

		TeamLead tl = teamLeadRepo.findById(team_lead_id)
				.orElseThrow(() -> new ResourceNotFoundException("TeamLead", "Id", team_lead_id));
		existing_dev.setTeamLead(tl);
		developerRepo.save(existing_dev);

		return "Team Lead of the developer is successfully updated";
	}

	public String deleteDeveloper(String id) {
		// TODO Auto-generated method stub
		Developer dev = developerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Developer", "Id", id));
		developerRepo.deleteById(id);
		return "Developer deleted Successfully";
	}

}
