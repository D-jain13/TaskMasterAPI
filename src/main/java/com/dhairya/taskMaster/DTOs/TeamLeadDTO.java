package com.dhairya.taskMaster.DTOs;

import java.util.Set;

import com.dhairya.taskMaster.enums.Department;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern.Flag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamLeadDTO {

	@NotBlank(message = "Name is mandatory")
	@Size(min = 5, max = 50, message = "Name of the employee must be between of range 5-50 characters")
	private String full_name;
	
	@NotBlank(message = "Email is mandatory")
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",flags = Flag.CASE_INSENSITIVE)
	private String email;
	
	@NotBlank(message = "Password is mandatory")
	@Size(min = 8, message = "Password should be of min 8 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).*$", message="Password must contain at least one letter and one number" )
	private String password;
	
	private Department department;
	
	@NotBlank(message = "Project Manager ID cannot be null")
	private String project_manager_id;
	
	private Set<String> developers_id;
	
	private Set<String> project_id;
	
}
