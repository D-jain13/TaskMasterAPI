package com.dhairya.taskMaster.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.enums.Priority;
import com.dhairya.taskMaster.enums.Status;
import com.dhairya.taskMaster.enums.SubDepartment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Table(indexes =  {
	@Index(columnList = "department ASC"),
	@Index(columnList = "subDepartment ASC"),
	@Index(columnList = "due_date DESC"),	
	@Index(columnList = "priority ASC"),
	@Index(columnList = "status ASC"),
	@Index(columnList = "task_created_at ASC"),
	@Index(columnList = "assigned_by_id"),
	@Index(columnList = "assigned_to_id")
	})
public class Task implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private String name;
	
	private String description;
	
	private LocalDateTime due_date;
	
	private LocalDateTime task_created_at;
	
	private LocalDateTime task_updated_at;
	
	private LocalDateTime task_starting_date;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	@Enumerated(EnumType.STRING)
	private Department department;
	
	@Enumerated(EnumType.STRING)
	private SubDepartment subDepartment;
	
	private int year;
	
	@ManyToOne
	@JoinColumn(name = "assigned_by_id")
	@JsonBackReference
	private Employee assigned_by;
	
	@ManyToOne
	@JoinColumn(name = "assigned_to_id")
	@JsonBackReference
	private Employee assigned_to;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	@JsonBackReference
	private Project project;
	
	@OneToMany(mappedBy = "task",cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonManagedReference
	private List<SupportingFile> supportingFiles;
	
}


