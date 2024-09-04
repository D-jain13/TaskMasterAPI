package com.dhairya.taskMaster.entity;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String token;
	private Instant expiryDate;
	
	@OneToOne
	@JoinColumn(name = "emp_id")
	private Employee employee;
}
