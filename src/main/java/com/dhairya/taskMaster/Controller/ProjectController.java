package com.dhairya.taskMaster.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhairya.taskMaster.DTOs.ProjectDTO;
import com.dhairya.taskMaster.entity.Project;
import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.enums.Status;
import com.dhairya.taskMaster.enums.SubDepartment;
import com.dhairya.taskMaster.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@GetMapping("/getAll")
	public ResponseEntity<Page<Project>> getAllProjects(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Page<Project>>(projectService.getAllProjects(pageable), HttpStatus.OK);

	}

	@GetMapping("/getById")
	public ResponseEntity<Project> getProjectById(@RequestParam String project_id)  {
		return new ResponseEntity<Project>(projectService.getProjectById(project_id), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<String> createProject(@Valid @RequestBody ProjectDTO projectDTO)  {
		return new ResponseEntity<String>(projectService.createProject(projectDTO), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateProjectById(@Valid @RequestParam String project_id, @RequestBody ProjectDTO projectDTO)
	 {
		return new ResponseEntity<String>(projectService.updateProjectById(project_id, projectDTO), HttpStatus.OK);
	}

	@PatchMapping("/changeStatus")
	public ResponseEntity<String> updateStatusOfProject(@RequestParam String project_id, @RequestParam Status status)
	{
		return new ResponseEntity<String>(projectService.updateStatusOfProject(project_id, status), HttpStatus.OK);
	}

	@PatchMapping("/changeDepartment")
	public ResponseEntity<String> updateDepartmentOfProject(@RequestParam String project_id,
			@RequestParam Department department) {
		return new ResponseEntity<String>(projectService.updateDepartmentOfProject(project_id, department),
				HttpStatus.OK);
	}
	
	@PatchMapping("/changeSubDepartment")
	public ResponseEntity<String> updateSubDepartmentOfProject(@RequestParam String project_id,
			@RequestParam SubDepartment subdepartment) {
		return new ResponseEntity<String>(projectService.updateSubDepartmentOfProject(project_id, subdepartment),
				HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteProjectById(@RequestParam String project_id) {
		return new ResponseEntity<String>(projectService.deleteProject(project_id), HttpStatus.OK);
	}
}
