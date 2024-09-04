package com.dhairya.taskMaster.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
		@Index(columnList = "employee_id"),
		@Index(columnList = "reportDate"),	
})
public class DailyProgressReport implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "employee_id",nullable = false)
	@JsonBackReference
	private Employee employee;
	
	private LocalDate reportDate;
	 
	@OneToMany(mappedBy = "dailyProgressReport", cascade = CascadeType.ALL, orphanRemoval = true)   
	@JsonManagedReference
	private List<DailyReport> dailyReport;
	
}
