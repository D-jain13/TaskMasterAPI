package com.dhairya.taskMaster.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dhairya.taskMaster.DTOs.ProjectManagerDTO;
import com.dhairya.taskMaster.entity.HeadOfDepartment;
import com.dhairya.taskMaster.entity.Project;
import com.dhairya.taskMaster.entity.ProjectManager;
import com.dhairya.taskMaster.entity.Task;
import com.dhairya.taskMaster.entity.TeamLead;
import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.enums.Role;
import com.dhairya.taskMaster.exception.ResourceAlreadyExistsException;
import com.dhairya.taskMaster.exception.ResourceNotFoundException;
import com.dhairya.taskMaster.repository.HODRepo;
import com.dhairya.taskMaster.repository.ProjectManagerRepo;

@Service
public class ProjectManagerService {

	@Autowired
	ProjectManagerRepo projectManagerRepo;

	@Autowired
	HODRepo hodRepo;

	@Autowired
	PasswordEncoder encoder;
	
	public Page<ProjectManager> getAllProjectManagers(Pageable pageable) {

		return projectManagerRepo.findAll(pageable);
	}

	public ProjectManager getByIdProjectManager(String id) {

		return projectManagerRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
	}

	public String createProjectManager(ProjectManagerDTO projectManagerDTO) {

		Optional<ProjectManager> pm = projectManagerRepo.findByEmail(projectManagerDTO.getEmail());

		if (pm.isPresent()) {
			throw new ResourceAlreadyExistsException("Project Manager", "Email", pm.get().getEmail());
		} else {
			pm = Optional.of(convertDtoToPM(projectManagerDTO));
			projectManagerRepo.save(pm.get());
			return "Project Manager Created Successfully";
		}

	}

	private ProjectManager convertDtoToPM(ProjectManagerDTO projectManagerDTO) {

		ProjectManager new_pm = new ProjectManager();
		new_pm.setFull_name(projectManagerDTO.getFull_name());
		new_pm.setEmail(projectManagerDTO.getEmail());
		new_pm.setPassword(encoder.encode(projectManagerDTO.getPassword()));
		new_pm.setDepartment(projectManagerDTO.getDepartment());
		new_pm.setCreatedDate(LocalDateTime.now());
		new_pm.setLastUpdatedAt(LocalDateTime.now());
		new_pm.setRole(Role.PM);
		new_pm.setTaskAssignedBy(new HashSet<Task>());
		new_pm.setTaskAssignedTo(new HashSet<Task>());

		HeadOfDepartment hod = hodRepo.findById(projectManagerDTO.getHead_of_department_id())
				.orElseThrow(() -> new ResourceNotFoundException("Head Od Department", "Id",
						projectManagerDTO.getHead_of_department_id()));
		new_pm.setHod(hod);

		new_pm.setProject(new HashSet<Project>());
		new_pm.setTeamLead(new HashSet<TeamLead>());

		return new_pm;
	}

	public String updateProjectManager(String id, ProjectManagerDTO projectManagerDTO) {

		ProjectManager existing_pm = projectManagerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project Manager", "Id", id));

		existing_pm.setFull_name(projectManagerDTO.getFull_name());
		existing_pm.setEmail(projectManagerDTO.getEmail());
		existing_pm.setPassword(encoder.encode(projectManagerDTO.getPassword()));
		existing_pm.setDepartment(projectManagerDTO.getDepartment());
		existing_pm.setLastUpdatedAt(LocalDateTime.now());

		HeadOfDepartment hod = hodRepo.findById(projectManagerDTO.getHead_of_department_id())
				.orElseThrow(() -> new ResourceNotFoundException("Head Of Department", "Id",
						projectManagerDTO.getHead_of_department_id()));

		existing_pm.setHod(hod);

		projectManagerRepo.save(existing_pm);

		return "Project Manager Updated";

	}

	public String updateDepartmentOfProjectManager(String id, Department department) {

		ProjectManager existing_pm = projectManagerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project Manager", "Id", id));

		existing_pm.setDepartment(department);
		projectManagerRepo.save(existing_pm);
		return "Department of Project Manager is successfully updated";
	}

	public String deleteProjectManager(String id) {
		// TODO Auto-generated method stub
		ProjectManager pm = projectManagerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project Manager", "Id", id));
		projectManagerRepo.deleteById(id);
		return "Project Manager is deleted";
	}

}
