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
import com.dhairya.taskMaster.entity.Task;
import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.service.HeadOfDepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hod")
public class HODController {

	@Autowired
	HeadOfDepartmentService headOfDepartmentService;
	
	@GetMapping("/getAll")
	public ResponseEntity<Page<HeadOfDepartment>> getAllHod(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size){
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Page<HeadOfDepartment>>(headOfDepartmentService.getAllHod(pageable),HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<HeadOfDepartment> getHodById(@RequestParam String id){
		return new ResponseEntity<HeadOfDepartment>(headOfDepartmentService.getHodById(id),HttpStatus.OK);
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<String> createHod(@Valid @RequestBody HeadOfDepartmentDTO hodDto){
		return new ResponseEntity<String>(headOfDepartmentService.createHod(hodDto),HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateHod(@RequestParam String id, @Valid @RequestBody HeadOfDepartmentDTO headOfDepartmentDTO){
		return new ResponseEntity<String>(headOfDepartmentService.updateHod(id,headOfDepartmentDTO),HttpStatus.OK);
	}
	
	@PatchMapping("/updateDepartment")
	public ResponseEntity<String> updateDepartmentOfHod(@RequestParam String id, @RequestParam Department department){
		return new ResponseEntity<String>(headOfDepartmentService.updateDepartmentOfHod(id,department),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteHod(@RequestParam String id){
		return new ResponseEntity<String>(headOfDepartmentService.deleteHod(id),HttpStatus.OK);
	}
}
