package com.dhairya.taskMaster.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity	
@Getter
@Setter
@Table(indexes = {
		@Index(columnList =  "daily_progress_report_id"),
		@Index(columnList =  "task_id"),
})
public class DailyReport implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "daily_progress_report_id",nullable = false)
	@JsonBackReference
	private DailyProgressReport dailyProgressReport;
	
	@ManyToOne
	@JoinColumn(name = "task_id",nullable = false)
	@JsonBackReference
	private Task tasks;
	
	private String work_desc;
	
	private int hours_spent;
	
	private int minutes_spent;
	
}
