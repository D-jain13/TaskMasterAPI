package com.dhairya.taskMaster.DTOs;

import java.util.Set;

import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.enums.Status;
import com.dhairya.taskMaster.enums.SubDepartment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {

	@NotBlank(message = "Name is mandatory")
	@Size(min = 5, max = 50, message = "Name of the Project must be between of range 5-50 characters")
	private String project_name;
	
	@NotBlank(message = "Team lead id cannot be empty")
	private String team_lead_id;
	
	@NotBlank(message = "Project Manager id cannot be empty")
	private String project_manager_id;
	
	
	private Department department;
	
	
	private SubDepartment subDepartment;
	
	private Status status;
	
	@NotBlank(message = "Description of Project cannot be empty")
	private String description;
	
	private Set<String> task_id;

	private Set<String> developer_id;
	
}
