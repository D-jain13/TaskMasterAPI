package com.dhairya.taskMaster.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dhairya.taskMaster.DTOs.TaskDTO;
import com.dhairya.taskMaster.entity.Employee;
import com.dhairya.taskMaster.entity.Project;
import com.dhairya.taskMaster.entity.SupportingFile;
import com.dhairya.taskMaster.entity.Task;
import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.enums.Priority;
import com.dhairya.taskMaster.enums.Status;
import com.dhairya.taskMaster.exception.ResourceNotFoundException;
import com.dhairya.taskMaster.repository.EmpRepo;
import com.dhairya.taskMaster.repository.ProjectRepo;
import com.dhairya.taskMaster.repository.SupportingDocumentRepo;
import com.dhairya.taskMaster.repository.TaskRepo;

@Service
public class TaskService {

	
	@Autowired
	TaskRepo taskRepo;

	@Autowired
	ProjectRepo projectRepo;

	@Autowired
	EmpRepo empRepo;

	@Autowired
	SupportingFileStorageService fileStorageService;

	@Autowired
	SupportingDocumentRepo documentRepo;

//	@Cacheable(value = "allTasks", key = "#root.methodName")
	@Transactional(readOnly = true)
	public Page<Task> getAllTasks(Pageable pageable) {
		Page<Task> tasks= taskRepo.findAll(pageable);
		tasks.forEach(task -> Hibernate.initialize(task.getSupportingFiles()));
		   
		return tasks;
	}

	
	public Task getTaskById(String id) {
		// TODO Auto-generated method stub

		Task task = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));

		return task;
	}

	public String createTask(TaskDTO taskDto) {
		// TODO Auto-generated method stub
		Task task = convertDtoToTask(taskDto);

		taskRepo.save(task);
		return "Task created successfully";
	}

	private Task convertDtoToTask(TaskDTO taskDto) {
		// TODO Auto-generated method stub

		Task task = new Task();
		task.setName(taskDto.getName());
		task.setDescription(taskDto.getDescription());
		task.setDue_date(taskDto.getDue_date());
		task.setTask_created_at(LocalDateTime.now());
		task.setTask_updated_at(LocalDateTime.now());
		task.setTask_starting_date(taskDto.getTask_starting_date());
		task.setStatus(taskDto.getStatus());
		task.setPriority(taskDto.getPriority());
		task.setYear(LocalDate.now().getYear());

		Employee assignor = empRepo.findById(taskDto.getEmp_assigned_by_id())
				.orElseThrow(() -> new ResourceNotFoundException("Employee who assigned the task", "Id",
						taskDto.getEmp_assigned_by_id()));

		task.setAssigned_by(assignor);

		Employee assignee = empRepo.findById(taskDto.getEmp_assigned_to_id())
				.orElseThrow(() -> new ResourceNotFoundException("Employee who assigned the task", "Id",
						taskDto.getEmp_assigned_to_id()));

		task.setAssigned_to(assignee);

		task.setDepartment(taskDto.getDepartment());
		task.setSubDepartment(taskDto.getSubDepartment());

		Project project = projectRepo.findById(taskDto.getProject_id())
				.orElseThrow(() -> new ResourceNotFoundException("Project", "Id", taskDto.getProject_id()));

		task.setProject(project);

		return task;
	}

	public String updateTask(String id, TaskDTO taskDTO) {
		// TODO Auto-generated method stub
		Task existingTask = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
		existingTask = convertDtoToTask(taskDTO);
		taskRepo.save(existingTask);
		return "Task is updated successfully";
	}

	public String updateStatusOfTask(String id, Status status) {
		// TODO Auto-generated method stub
		Task existingTask = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
		existingTask.setStatus(status);
		taskRepo.save(existingTask);
		return "Status Updated to " + status;
	}

	public String updatePriorityOfTask(String id, Priority priority) {
		// TODO Auto-generated method stub
		Task existingTask = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
		existingTask.setPriority(priority);
		taskRepo.save(existingTask);
		return "Priority Changed to " + priority;
	}

	public String updateProjectOfTask(String task_id, String project_id) {
		// TODO Auto-generated method stub
		Task existingTask = taskRepo.findById(task_id)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "Id", task_id));

		Project project = projectRepo.findById(project_id)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "Id", project_id));

		existingTask.setProject(project);
		taskRepo.save(existingTask);
		return "Project of task is updated to" + project.getProject_name();
	}

	public String updateDepartmentOfTask(String task_id, Department department) {
		// TODO Auto-generated method stub

		Task existingTask = taskRepo.findById(task_id)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "Id", task_id));

		existingTask.setDepartment(department);
		taskRepo.save(existingTask);
		return "Department of task is updated to" + department;
	}

	public String deleteTaskById(String id) {
		// TODO Auto-generated method stub
		Task existingTask = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));

		taskRepo.deleteById(id);
		return "Task Deleted Successfully";
	}

	public String uploadSupportingDocuments(String task_id, MultipartFile[] files) throws Exception {
		Task existingTask = taskRepo.findById(task_id)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "Id", task_id));

		List<SupportingFile> supportingFileList = new ArrayList<>();

		for (MultipartFile file : files) {

			String path = fileStorageService.save(file);

			SupportingFile supportingFile = new SupportingFile();
			supportingFile.setFileName(file.getOriginalFilename());
			supportingFile.setFileSize(file.getSize());
			supportingFile.setFileType(file.getContentType());
			supportingFile.setTask(existingTask);
			supportingFile.setUploaded_at(LocalDateTime.now());
			supportingFile.setPath(path);

			documentRepo.save(supportingFile);
			existingTask.getSupportingFiles().add(supportingFile);
			supportingFileList.add(supportingFile);

			taskRepo.save(existingTask);

		}

		return "Files are uploaded successfully";
	}

	public ByteArrayOutputStream downloadSupportingDocuments(String task_id) throws Exception {
		Task existingTask = taskRepo.findById(task_id)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "Id", task_id));

		List<File> supportingDocumentList = existingTask.getSupportingFiles().stream()
				.map(file -> new File(file.getPath())).collect(Collectors.toList());
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		SupportingFileStorageService.getZip(supportingDocumentList,byteArrayOutputStream);
		
		return byteArrayOutputStream;
	}
}
