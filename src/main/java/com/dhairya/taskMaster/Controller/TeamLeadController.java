package com.dhairya.taskMaster.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhairya.taskMaster.DTOs.TeamLeadDTO;
import com.dhairya.taskMaster.entity.TeamLead;
import com.dhairya.taskMaster.service.TeamLeadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/teamLead")
public class TeamLeadController {

	@Autowired
	TeamLeadService teamLeadService;
	
	@GetMapping("/getAll")
	public ResponseEntity<Page<TeamLead>> getAllTeamLead(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size){
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Page<TeamLead>>(teamLeadService.getAllTeamLead(pageable),HttpStatus.OK);
	}
	
	@GetMapping("/getById")
	public ResponseEntity<TeamLead> getByIdTeamLead(@RequestParam String id){
		return new ResponseEntity<TeamLead>(teamLeadService.getByIdTeamLead(id),HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createTeamLead(@Valid @RequestBody TeamLeadDTO teamLeadDTO){
		return new ResponseEntity<String>(teamLeadService.createTeamLead(teamLeadDTO),HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateTeamLead(@RequestParam String id,@Valid @RequestBody TeamLeadDTO teamLeadDTO){
		return new ResponseEntity<String>(teamLeadService.updateTeamLead(id,teamLeadDTO),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteById(@RequestParam String id){
		return new ResponseEntity<String>(teamLeadService.deleteById(id),HttpStatus.OK);
	}
}
