package com.dhairya.taskMaster.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Employee implements Serializable  {

	@Id
	@GeneratedValue(strategy =GenerationType.UUID)
	private String id;
	
	@Column(nullable = false)
	private String full_name;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	@JsonIgnore
	private String password;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime lastUpdatedAt;
	
	@Enumerated(EnumType.STRING)
	private Department department;
	
	@OneToMany(mappedBy = "assigned_by")
	@JsonManagedReference
	private Set<Task> taskAssignedBy;
	
	@OneToMany(mappedBy = "assigned_to")
	@JsonManagedReference
	private Set<Task> taskAssignedTo;
	
	@Enumerated(EnumType.STRING)
	private Role role;
}

