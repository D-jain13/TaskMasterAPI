package com.dhairya.taskMaster.entity;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class HeadOfDepartment extends Employee implements Serializable  {
	
	@OneToMany(mappedBy = "hod",cascade = CascadeType.DETACH,orphanRemoval = true)
	@JsonManagedReference
	private Set<ProjectManager> projectManager;
	
}
