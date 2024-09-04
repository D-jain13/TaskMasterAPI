package com.dhairya.taskMaster.entity;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TeamLead extends Employee implements Serializable  {
	
	@ManyToOne
	@JoinColumn(name = "project_manager_id")
	@JsonBackReference
	private ProjectManager projectManager;
	
	@OneToMany(mappedBy = "teamLead",cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonIgnore
	private Set<Developer> developers;
	
	@OneToMany(mappedBy = "teamLead")
	@JsonManagedReference
	private Set<Project> projects;

}
