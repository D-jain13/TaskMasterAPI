package com.dhairya.taskMaster.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dhairya.taskMaster.DTOs.DailyProgressReportDTO;
import com.dhairya.taskMaster.entity.DailyProgressReport;
import com.dhairya.taskMaster.entity.DailyReport;
import com.dhairya.taskMaster.entity.Employee;
import com.dhairya.taskMaster.entity.Task;
import com.dhairya.taskMaster.exception.ResourceNotFoundException;
import com.dhairya.taskMaster.repository.DailyProgressReportRepo;
import com.dhairya.taskMaster.repository.EmpRepo;
import com.dhairya.taskMaster.repository.TaskRepo;

@Service
public class DailyProgressReportService {

	@Autowired
	DailyProgressReportRepo dprRepo;

	@Autowired
	EmpRepo empRepo;

	@Autowired
	TaskRepo taskRepo;

	public Page<DailyProgressReport> getAllDpr(Pageable pageable) {

		return dprRepo.findAll(pageable);
	}

	public DailyProgressReport getById(String id) {

		DailyProgressReport dailyProgressReport = dprRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Daily Progress Report", "Id", id));
		return dailyProgressReport;
	}

	public Page<DailyProgressReport> getByEmpId(String emp_id,Pageable pageable) {
		Employee emp = empRepo.findById(emp_id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", emp_id));
		return dprRepo.findByEmployeeId(emp_id,pageable);
	}

	public Page<DailyProgressReport> getByDate(LocalDate date,Pageable pageable) {
		
		Page<DailyProgressReport> list = dprRepo.findByReportDate(date,pageable);
		return list;
	}
	
	public DailyProgressReport getByEmpIDAndDate(String emp_id, LocalDate date) {
		// TODO Auto-generated method stub
		Employee emp = empRepo.findById(emp_id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", emp_id));
		DailyProgressReport dpr = dprRepo.findByEmployeeIdAndReportDate(emp_id, date);
		return dpr;
	}

	public String createDPR(DailyProgressReportDTO dailyProgressReportDTO) {
		Employee emp = empRepo.findById(dailyProgressReportDTO.getEmp_id())
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", dailyProgressReportDTO.getEmp_id()));

		DailyProgressReport dailyProgressReport = new DailyProgressReport();
		dailyProgressReport.setEmployee(emp);
		dailyProgressReport.setReportDate(LocalDate.parse(dailyProgressReportDTO.getReport_date()));

		List<DailyReport> dailyReports = dailyProgressReportDTO.getDailyReportObject().stream().map(dailyReportDTO -> {
			Task task = taskRepo.findById(dailyReportDTO.getTask_id())
					.orElseThrow(() -> new ResourceNotFoundException("Task", "Id", dailyReportDTO.getTask_id()));
			DailyReport dailyReport = new DailyReport();
			dailyReport.setTasks(task);
			dailyReport.setDailyProgressReport(dailyProgressReport);
			dailyReport.setHours_spent(dailyReportDTO.getHours_spent());
			dailyReport.setMinutes_spent(dailyReportDTO.getMinutes_spent());
			dailyReport.setWork_desc(dailyReportDTO.getWork_desc());
			return dailyReport;
		}).collect(Collectors.toList());

		dailyProgressReport.setDailyReport(dailyReports);
		dprRepo.save(dailyProgressReport);

		return "Daily Progress is created successfully";
	}

	
	public String updateDPR(String id, DailyProgressReportDTO dailyProgressReportDTO) {
	   
		// to fetch existing report
	    DailyProgressReport existingReport = dprRepo.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Daily Project Report", "Id", id));

	    // to update employee if needed
	    if (!existingReport.getEmployee().getId().equals(dailyProgressReportDTO.getEmp_id())) {
	        Employee emp = empRepo.findById(dailyProgressReportDTO.getEmp_id()).orElseThrow(
	                () -> new ResourceNotFoundException("Employee", "Id", dailyProgressReportDTO.getEmp_id()));
	        existingReport.setEmployee(emp);
	    }

	    // Update report date
	    existingReport.setReportDate(LocalDate.parse(dailyProgressReportDTO.getReport_date()));

	    // map of task IDs to DailyReport objects for easy finding
	    Map<String, DailyReport> dailyReportMap = existingReport.getDailyReport().stream()
	            .collect(Collectors.toMap(dr -> dr.getTasks().getId(), dr -> dr));

	    List<DailyReport> updatedReports = dailyProgressReportDTO.getDailyReportObject().stream()
	            .map(dailyReportDTO -> {
	                DailyReport dailyReport = dailyReportMap.get(dailyReportDTO.getTask_id());

	                if (dailyReport != null) {
	                    // Updating existing report here
	                    dailyReport.setHours_spent(dailyReportDTO.getHours_spent());
	                    dailyReport.setMinutes_spent(dailyReportDTO.getMinutes_spent());
	                    dailyReport.setWork_desc(dailyReportDTO.getWork_desc());
	                } else {
	                    // Creating a new report
	                    Task task = taskRepo.findById(dailyReportDTO.getTask_id())
	                            .orElseThrow(() -> new ResourceNotFoundException("Task", "Id", dailyReportDTO.getTask_id()));
	                    dailyReport = new DailyReport();
	                    dailyReport.setTasks(task);
	                    dailyReport.setDailyProgressReport(existingReport);
	                    dailyReport.setHours_spent(dailyReportDTO.getHours_spent());
	                    dailyReport.setMinutes_spent(dailyReportDTO.getMinutes_spent());
	                    dailyReport.setWork_desc(dailyReportDTO.getWork_desc());
	                    System.out.println("Creating new object");
	                }
	                return dailyReport;
	            })
	            .collect(Collectors.toList());

	    // first clearing the list and then adding the updated list
	    existingReport.getDailyReport().clear();
	    existingReport.getDailyReport().addAll(updatedReports);

	    dprRepo.save(existingReport);

	    return "Daily Project Report is updated successfully";
	}

	public String deleteDPR(String id) {

		DailyProgressReport dpr = dprRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Daily Progress Report", "Id", id));
		dprRepo.deleteById(id);
		return "Daily Progress Report is deleted successfully";
	}


}
