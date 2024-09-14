package com.dhairya.taskMaster.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dhairya.taskMaster.DTOs.HeadOfDepartmentDTO;
import com.dhairya.taskMaster.entity.HeadOfDepartment;
import com.dhairya.taskMaster.entity.ProjectManager;
import com.dhairya.taskMaster.entity.Task;
import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.exception.ResourceAlreadyExistsException;
import com.dhairya.taskMaster.exception.ResourceNotFoundException;
import com.dhairya.taskMaster.repository.HODRepo;
import com.dhairya.taskMaster.repository.ProjectManagerRepo;

@Service
public class HeadOfDepartmentService {

	@Autowired
	HODRepo hodRepo;

	@Autowired
	ProjectManagerRepo projectManagerRepo;

	// To get list of all hod
	public Page<HeadOfDepartment> getAllHod(Pageable pageable) {

		return hodRepo.findAll(pageable);
	}

	// To get specific hod by id
	public HeadOfDepartment getHodById(String id) {

		HeadOfDepartment hod = hodRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Head Of Department", "Id", id));
		return hod;
	}

	// To create hod
	public String createHod(HeadOfDepartmentDTO hodDto) {
		Optional<HeadOfDepartment> hod = hodRepo.findByEmail(hodDto.getEmail());
		
		if(hod.isPresent()) {
			throw new ResourceAlreadyExistsException("Head Of Department", "Email", hod.get().getEmail());
		}
		else {
			hod = Optional.of(convertDtoToHod(hodDto));
			hodRepo.save(hod.get());
			return "Hod Created Successfully";
		}
	}

	// To convert dto object to HOD
	private HeadOfDepartment convertDtoToHod(HeadOfDepartmentDTO hodDto) {

		HeadOfDepartment new_hod = new HeadOfDepartment();

		new_hod.setFull_name(hodDto.getFull_name());
		new_hod.setEmail(hodDto.getEmail());
		new_hod.setPassword(hodDto.getPassword());
		new_hod.setCreatedDate(LocalDateTime.now());
		new_hod.setLastUpdatedAt(LocalDateTime.now());
		new_hod.setDepartment(hodDto.getDepartment());
		new_hod.setTaskAssignedBy(new HashSet<Task>());
		new_hod.setTaskAssignedTo(new HashSet<Task>());

		Set<ProjectManager> pm_set = new HashSet<>();

		if(hodDto.getProject_manager_id().isEmpty()) {
			new_hod.setProjectManager(pm_set);
		}
		else {
			
			ProjectManager projectManager = projectManagerRepo.findById(hodDto.getProject_manager_id()).orElseThrow(
					() -> new ResourceNotFoundException("Project Manager", "Id", hodDto.getProject_manager_id()));
			
		
			pm_set.add(projectManager);
			
			new_hod.setProjectManager(pm_set);
		}

		return new_hod;
	}

	// To update HOD
	public String updateHod(String hod_id, HeadOfDepartmentDTO headOfDepartmentDTO) {

		HeadOfDepartment existing_hod = hodRepo.findById(hod_id)
				.orElseThrow(() -> new ResourceNotFoundException("Head Of Department", "Id", hod_id));

		existing_hod.setFull_name(headOfDepartmentDTO.getFull_name());
		existing_hod.setEmail(headOfDepartmentDTO.getEmail());
		existing_hod.setPassword(headOfDepartmentDTO.getPassword());
		existing_hod.setDepartment(headOfDepartmentDTO.getDepartment());

		existing_hod.setLastUpdatedAt(LocalDateTime.now());

		hodRepo.save(existing_hod);

		return "Head Of Department is updated successfully";
	}

	// To update department of HOD
	public String updateDepartmentOfHod(String hod_id, Department department) {

		HeadOfDepartment existing_hod = hodRepo.findById(hod_id)
				.orElseThrow(() -> new ResourceNotFoundException("Head Of Department", "Id", hod_id));

		existing_hod.setDepartment(department);
		hodRepo.save(existing_hod);
		return "Department of HOD is successfully updated";
	}

	public String deleteHod(String id) {
		// TODO Auto-generated method stub
		HeadOfDepartment hod = hodRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Head of Department", "Id", id));
		hodRepo.deleteById(id);
		return "Head of Department is deleted successfully";
	}

}
