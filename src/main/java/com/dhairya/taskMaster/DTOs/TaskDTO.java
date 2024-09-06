package com.dhairya.taskMaster.DTOs;

import java.time.LocalDateTime;

import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.enums.Priority;
import com.dhairya.taskMaster.enums.Status;
import com.dhairya.taskMaster.enums.SubDepartment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {

	private String name;
	private String description;
	private LocalDateTime due_date;
	private LocalDateTime task_starting_date;
	private Status status;
	private Priority priority;
	private Department department;
	private SubDepartment subDepartment;
	private String emp_assigned_by_id;
	private String emp_assigned_to_id;
	private String project_id;
	
}
