package com.dhairya.taskMaster.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.enums.Status;
import com.dhairya.taskMaster.enums.SubDepartment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Project implements Serializable {

	@Id
	@GeneratedValue(strategy =GenerationType.UUID)
	private String id;
	
	private String project_name;
	
	@ManyToOne
	@JoinColumn(name="team_lead_id")
	@JsonBackReference
	private TeamLead teamLead;
	
	@ManyToOne
	@JoinColumn(name = "project_manager_id")
	@JsonBackReference
	private ProjectManager projectManager;
	
	@Enumerated(EnumType.STRING)
	private Department department;
	
	@Enumerated(EnumType.STRING)
	private SubDepartment subDepartment;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private String description;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;
	
	@OneToMany(mappedBy = "project",cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonManagedReference
	private Set<Task> tasks;

	@ManyToMany(mappedBy = "assignedProjects")
	@JsonManagedReference
	@JsonIgnore
	private Set<Developer> developers;
	
}
