package com.dhairya.taskMaster.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SupportingFile implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String fileName;
	private String fileType;
	private String path;
	private long fileSize;
	private LocalDateTime uploaded_at;
	
	@ManyToOne
	@JoinColumn(name = "task_id",nullable = false)
	@JsonBackReference
	private Task task;
	
	
}
