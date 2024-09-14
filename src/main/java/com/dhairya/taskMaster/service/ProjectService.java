package com.dhairya.taskMaster.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dhairya.taskMaster.DTOs.ProjectDTO;
import com.dhairya.taskMaster.entity.Developer;
import com.dhairya.taskMaster.entity.Project;
import com.dhairya.taskMaster.entity.ProjectManager;
import com.dhairya.taskMaster.entity.Task;
import com.dhairya.taskMaster.entity.TeamLead;
import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.enums.Status;
import com.dhairya.taskMaster.enums.SubDepartment;
import com.dhairya.taskMaster.exception.ResourceNotFoundException;
import com.dhairya.taskMaster.repository.DeveloperRepo;
import com.dhairya.taskMaster.repository.ProjectManagerRepo;
import com.dhairya.taskMaster.repository.ProjectRepo;
import com.dhairya.taskMaster.repository.TaskRepo;
import com.dhairya.taskMaster.repository.TeamLeadRepo;

@Service
public class ProjectService {

	@Autowired
	ProjectRepo projectRepo;

	@Autowired
	TeamLeadRepo teamLeadRepo;

	@Autowired
	TaskRepo taskRepo;

	@Autowired
	ProjectManagerRepo projectManagerRepo;

	@Autowired
	DeveloperRepo developerRepo;

	public Page<Project> getAllProjects(Pageable pageable) {
		 
		return projectRepo.findAll(pageable);
	}

	public Project getProjectById(String project_id) {
		 
		Project project = projectRepo.findById(project_id)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "Id", project_id));

		return project;
	}

	public String createProject(ProjectDTO projectDTO) {
		 

		Project project = convertDTOtoProject(projectDTO);
		projectRepo.save(project);
		return "Created project successfully";

	}

	private Project convertDTOtoProject(ProjectDTO projectDTO) {
		 
		Project project = new Project();
		project.setProject_name(projectDTO.getProject_name());
		project.setDepartment(projectDTO.getDepartment());
		project.setStatus(projectDTO.getStatus());
		project.setDescription(projectDTO.getDescription());

		TeamLead teamLead = teamLeadRepo.findById(projectDTO.getTeam_lead_id())
				.orElseThrow(() -> new ResourceNotFoundException("Team Lead", "Id", projectDTO.getTeam_lead_id()));

		project.setTeamLead(teamLead);

		ProjectManager projectManager = projectManagerRepo.findById(projectDTO.getProject_manager_id()).orElseThrow(
				() -> new ResourceNotFoundException("Project Manager", "Id", projectDTO.getProject_manager_id()));

		project.setProjectManager(projectManager);
		project.setSubDepartment(projectDTO.getSubDepartment());
		project.setCreatedDate(LocalDateTime.now());
		project.setUpdatedDate(LocalDateTime.now());

		if (projectDTO.getTask_id() == null || projectDTO.getTask_id().isEmpty()) {

			project.setTasks(new HashSet<Task>());
		} else {

			Set<Task> tasks = new HashSet<>();

			tasks = projectDTO.getTask_id().stream().map(task_id -> {

				return taskRepo.findById(task_id)
						.orElseThrow(() -> new ResourceNotFoundException("Task", "Id", task_id));

			}).collect(Collectors.toSet());

			tasks.forEach(task -> task.setProject(project));
			project.setTasks(tasks);

		}

		if (projectDTO.getDeveloper_id() == null || projectDTO.getDeveloper_id().isEmpty()) {
			System.out.println("NUll found");
			project.setDevelopers(new HashSet<Developer>());
		} else {

			Set<Developer> developer = new HashSet<>();

			developer = projectDTO.getDeveloper_id().stream().map(dev_id -> {

				return developerRepo.findById(dev_id)
						.orElseThrow(() -> new ResourceNotFoundException("Developer", "Id", dev_id));

			}).collect(Collectors.toSet());

			developer.forEach(dev -> dev.getAssignedProjects().add(project));
			project.setDevelopers(developer);

		}
		return project;
	}

	public String updateProjectById(String project_id, ProjectDTO projectDTO) {
		 
		Project existing_project = projectRepo.findById(project_id)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "Id", project_id));

		existing_project.setProject_name(projectDTO.getProject_name());

		TeamLead teamLead = teamLeadRepo.findById(projectDTO.getTeam_lead_id())
				.orElseThrow(() -> new ResourceNotFoundException("Team Lead", "Id", projectDTO.getTeam_lead_id()));

		existing_project.setTeamLead(teamLead);

		ProjectManager projectManager = projectManagerRepo.findById(projectDTO.getProject_manager_id()).orElseThrow(
				() -> new ResourceNotFoundException("Project Manager", "Id", projectDTO.getProject_manager_id()));

		existing_project.setProjectManager(projectManager);

		existing_project.setDepartment(projectDTO.getDepartment());
		existing_project.setSubDepartment(projectDTO.getSubDepartment());
		existing_project.setStatus(projectDTO.getStatus());
		existing_project.setDescription(projectDTO.getDescription());
		existing_project.setUpdatedDate(LocalDateTime.now());
		
		return "Project details are updated";

	}

	public String updateStatusOfProject(String project_id, Status status) {
		 
		Project project = projectRepo.findById(project_id)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "Id", project_id));

		project.setStatus(status);
		projectRepo.save(project);
		return "Project's Status has been updated to " + status;

	}

	public String updateDepartmentOfProject(String project_id, Department department) {
		 
		Project project = projectRepo.findById(project_id)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "Id", project_id));

		project.setDepartment(department);
		projectRepo.save(project);
		return "Project's Department has been updated to " + department;

	}
	
	public String updateSubDepartmentOfProject(String project_id, SubDepartment subDepartment) {
		 
		Project project = projectRepo.findById(project_id)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "Id", project_id));

		project.setSubDepartment(subDepartment);
		projectRepo.save(project);
		return "Project's SubDepartment has been updated to " + subDepartment;
 
	}

	public String deleteProject(String project_id) {
		 
		Project project = projectRepo.findById(project_id)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "Id", project_id));

		projectRepo.deleteById(project_id);
		return "Project is deleted succesfully";
	}

}
