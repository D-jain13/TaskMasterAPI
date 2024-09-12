package com.dhairya.taskMaster.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dhairya.taskMaster.entity.DailyProgressReport;

public interface DailyProgressReportRepo extends JpaRepository<DailyProgressReport, String> {
	
	Page<DailyProgressReport> findByEmployeeId(String employeeId,Pageable pageable);

	DailyProgressReport findByEmployeeIdAndReportDate(String employeeId, LocalDate reportdate);

	Page<DailyProgressReport> findByReportDate(LocalDate date,Pageable pageable);

}
