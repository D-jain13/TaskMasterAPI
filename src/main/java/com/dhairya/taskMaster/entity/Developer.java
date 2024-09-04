package com.dhairya.taskMaster.entity;

import java.io.Serializable;
import java.util.Set;

import com.dhairya.taskMaster.enums.SubDepartment;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Developer extends Employee implements Serializable  {

	@ManyToOne
	@JoinColumn(name = "team_lead_id")
	@JsonBackReference
	private TeamLead teamLead;

	@ManyToMany
	@JoinTable(name = "developer_project_table", 
			   joinColumns = @JoinColumn(name = "employee_id"), 
			   inverseJoinColumns = @JoinColumn(name = "project_id"))
	@JsonBackReference
	private Set<Project> assignedProjects;
	
	private SubDepartment subDepartment;
	
}
