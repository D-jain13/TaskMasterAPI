package com.dhairya.taskMaster.DTOs;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyProgressReportDTO {

	private String emp_id;
	private String report_date;
	private List<DailyReportDTO> dailyReportObject;
}
