package com.dhairya.taskMaster.entity;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProjectManager extends Employee implements Serializable {
	
	@ManyToOne
	@JoinColumn(name = "hod_id")
	@JsonBackReference
	private HeadOfDepartment hod;

	@OneToMany(mappedBy = "projectManager")
	@JsonManagedReference
	private Set<Project> project;
	
	
	@OneToMany(mappedBy = "projectManager",cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonManagedReference
	private Set<TeamLead> teamLead;

}
