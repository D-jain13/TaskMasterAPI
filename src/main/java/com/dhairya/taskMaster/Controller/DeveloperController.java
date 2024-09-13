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

import com.dhairya.taskMaster.DTOs.DeveloperDTO;
import com.dhairya.taskMaster.entity.Developer;
import com.dhairya.taskMaster.entity.Task;
import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.enums.Status;
import com.dhairya.taskMaster.service.DeveloperService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/developer")
public class DeveloperController {

	@Autowired
	DeveloperService developerService;

	// To get list of all the developers
	@GetMapping("/getAll")
	public ResponseEntity<Page<Developer>> getAllDeveloper(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable =PageRequest.of(page,size);
		return new ResponseEntity<Page<Developer>>(developerService.getAllDevelopers(pageable), HttpStatus.OK);
	}

	// To get developer by specific id
	@GetMapping("/getById")
	public ResponseEntity<Developer> getById(@RequestParam String id) {
		return new ResponseEntity<>(developerService.getById(id), HttpStatus.OK);
	}

	// To get all the tasks assigned to a developer
	@GetMapping("/getMyTasks")
	public ResponseEntity<?> getAllAssignedTasks(@RequestParam String id,
			@RequestParam Status status,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		
		Pageable pageable =PageRequest.of(page,size);
		
		Page<Task> assignedTask ;
		
		if(status!= Status.NONE) {
			assignedTask = developerService.getAllAssignedTasks(id,null,pageable);
		}
		else {
			assignedTask = developerService.getAllAssignedTasks(id, status,pageable);
		}
		
		if (!assignedTask.isEmpty()) {
			return new ResponseEntity<>(assignedTask, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Tasks Not Found", HttpStatus.OK);
		}
	}

	// To create a new developer
	@PostMapping("/create")
	public ResponseEntity<?> createDeveloper(@Valid @RequestBody DeveloperDTO developerDTO) {
		return new ResponseEntity<>(developerService.createDeveloper(developerDTO),HttpStatus.OK);

	}

	// To update a developer
	@PutMapping("/update")
	public ResponseEntity<String> updateDeveloper(@RequestParam String id, @Valid @RequestBody DeveloperDTO developerDTO) {
		return new ResponseEntity<String>(developerService.updateDeveloper(id, developerDTO), HttpStatus.OK);
	}

	// To update the department of developer
	@PatchMapping("/updateDepartment")
	public ResponseEntity<String> updateDepartment(@RequestParam String id, @RequestParam Department department) {
		return new ResponseEntity<String>(developerService.updateDepartment(id, department), HttpStatus.OK);
	}

	// To update the teamLead of developer
	@PatchMapping("/updateTeamLead")
	public ResponseEntity<String> updateTeamLead(@RequestParam String id, @RequestBody String team_lead_id) {
		return new ResponseEntity<String>(developerService.updateTeamLead(id, team_lead_id), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteDeveloper(@RequestParam String id){
		return new ResponseEntity<>(developerService.deleteDeveloper(id),HttpStatus.OK);
	}
}
