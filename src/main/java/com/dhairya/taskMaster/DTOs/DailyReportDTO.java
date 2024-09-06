package com.dhairya.taskMaster.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyReportDTO {

	private String id;
	private String task_id;
	private int hours_spent;
	private int minutes_spent;
	private String work_desc;
}
