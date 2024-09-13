package com.dhairya.taskMaster.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhairya.taskMaster.DTOs.DailyProgressReportDTO;
import com.dhairya.taskMaster.entity.DailyProgressReport;
import com.dhairya.taskMaster.service.DailyProgressReportService;

@RestController
@RequestMapping("/dpr")
public class DailyProgressReportController {

	@Autowired
	DailyProgressReportService dailyProgressReportService;

	@GetMapping("/getAll")
	public ResponseEntity<Page<DailyProgressReport>> getAllDpr(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) 
	{
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Page<DailyProgressReport>>(dailyProgressReportService.getAllDpr(pageable),
				HttpStatus.OK);
	}

	@GetMapping("/getById")
	public ResponseEntity<DailyProgressReport> getByID(@RequestParam String id) {
		return new ResponseEntity<DailyProgressReport>(dailyProgressReportService.getById(id), HttpStatus.OK);
	}

	@GetMapping("/getByEmpId")
	public ResponseEntity<Page<DailyProgressReport>> getByEmpID(@RequestParam String emp_id,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) 
	{
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Page<DailyProgressReport>>(dailyProgressReportService.getByEmpId(emp_id, pageable),
				HttpStatus.OK);
	}

	@GetMapping("/getByDate")
	public ResponseEntity<Page<DailyProgressReport>> getByDate(@RequestParam LocalDate date,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) 
	{

		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Page<DailyProgressReport>>(dailyProgressReportService.getByDate(date, pageable),
				HttpStatus.OK);
	}

	@GetMapping("/getByEmpIdAndDate")
	public ResponseEntity<DailyProgressReport> getByEmpIDAndDate(@RequestParam String emp_id,
			@RequestParam LocalDate date) {
		return new ResponseEntity<DailyProgressReport>(dailyProgressReportService.getByEmpIDAndDate(emp_id, date),
				HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<String> createDPR(@RequestBody DailyProgressReportDTO dailyProgressReportDTO) {
		return new ResponseEntity<String>(dailyProgressReportService.createDPR(dailyProgressReportDTO), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateDPR(@RequestParam String id,
			@RequestBody DailyProgressReportDTO dailyProgressReportDTO) {
		return new ResponseEntity<String>(dailyProgressReportService.updateDPR(id, dailyProgressReportDTO),
				HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteDPR(@RequestParam String id) {
		return new ResponseEntity<String>(dailyProgressReportService.deleteDPR(id), HttpStatus.OK);
	}
}
