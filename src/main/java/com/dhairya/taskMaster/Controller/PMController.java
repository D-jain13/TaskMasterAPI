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

import com.dhairya.taskMaster.DTOs.ProjectManagerDTO;
import com.dhairya.taskMaster.entity.ProjectManager;
import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.service.ProjectManagerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pm")
public class PMController {

	@Autowired
	ProjectManagerService projectManagerService;
	
	@GetMapping("/getAll")
	public ResponseEntity<Page<ProjectManager>> getAllProjectManagers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size){
		Pageable pageable = PageRequest.of(page, size);

		return new ResponseEntity<Page<ProjectManager>>(projectManagerService.getAllProjectManagers(pageable),HttpStatus.OK);
	}
	
	@GetMapping("/getById")
	public ResponseEntity<ProjectManager> getByIdProjectManager(@RequestParam String id){
		return new ResponseEntity<>(projectManagerService.getByIdProjectManager(id),HttpStatus
				.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createProjectManager(@Valid @RequestBody ProjectManagerDTO projectManagerDTO){
		return new ResponseEntity<String>(projectManagerService.createProjectManager(projectManagerDTO),HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateProjectManager(@RequestParam String id,@Valid @RequestBody ProjectManagerDTO projectManagerDTO){
		return new ResponseEntity<String>(projectManagerService.updateProjectManager(id,projectManagerDTO),HttpStatus.OK);
	}
	
	@PatchMapping("/updateDepartment")
	public ResponseEntity<String> updateDepartmentOfProjectManager(@RequestParam String id, @RequestParam Department department){
		return new ResponseEntity<String>(projectManagerService.updateDepartmentOfProjectManager(id,department),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteProjectManager(@RequestParam String id){
		return new ResponseEntity<String>(projectManagerService.deleteProjectManager(id),HttpStatus.OK);
	}
}
