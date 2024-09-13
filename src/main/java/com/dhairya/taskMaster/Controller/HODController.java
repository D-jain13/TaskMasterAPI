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

import com.dhairya.taskMaster.DTOs.HeadOfDepartmentDTO;
import com.dhairya.taskMaster.entity.HeadOfDepartment;
import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.service.HeadOfDepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hod")
public class HODController {

	@Autowired
	HeadOfDepartmentService headOfDepartmentService;
	
	//To get all the hod present in db 
	@GetMapping("/getAll")
	public ResponseEntity<Page<HeadOfDepartment>> getAllHod(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size){
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Page<HeadOfDepartment>>(headOfDepartmentService.getAllHod(pageable),HttpStatus.OK);
	}
	
	//To get hod by id
	@GetMapping("/getById")
	public ResponseEntity<HeadOfDepartment> getHodById(@RequestParam String hod_id){
		return new ResponseEntity<HeadOfDepartment>(headOfDepartmentService.getHodById(hod_id),HttpStatus.OK);
	}
	
	//To create hod
	@PostMapping("/create")
	public ResponseEntity<String> createHod(@Valid @RequestBody HeadOfDepartmentDTO hodDto){
		return new ResponseEntity<String>(headOfDepartmentService.createHod(hodDto),HttpStatus.OK);
	}
	
	//To update hod
	@PutMapping("/update")
	public ResponseEntity<String> updateHod(@RequestParam String hod_id, @Valid @RequestBody HeadOfDepartmentDTO headOfDepartmentDTO){
		return new ResponseEntity<String>(headOfDepartmentService.updateHod(hod_id,headOfDepartmentDTO),HttpStatus.OK);
	}
	
	//To update department of hod
	@PatchMapping("/updateDepartment")
	public ResponseEntity<String> updateDepartmentOfHod(@RequestParam String hod_id, @RequestParam Department department){
		return new ResponseEntity<String>(headOfDepartmentService.updateDepartmentOfHod(hod_id,department),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteHod(@RequestParam String id){
		return new ResponseEntity<String>(headOfDepartmentService.deleteHod(id),HttpStatus.OK);
	}
}
