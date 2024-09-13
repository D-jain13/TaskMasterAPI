package com.dhairya.taskMaster.Controller;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dhairya.taskMaster.DTOs.TaskDTO;
import com.dhairya.taskMaster.entity.Task;
import com.dhairya.taskMaster.enums.Department;
import com.dhairya.taskMaster.enums.Priority;
import com.dhairya.taskMaster.enums.Status;
import com.dhairya.taskMaster.service.TaskService;

import jakarta.persistence.Cacheable;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	TaskService taskService;

	@GetMapping("/getAllTask")
	public ResponseEntity<Page<Task>> getAllTasks(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Page<Task>>(taskService.getAllTasks(pageable), HttpStatus.OK);
	}

	@GetMapping("/getTaskById")
	public ResponseEntity<Task> getTaskByID(@RequestParam String id) {
		return new ResponseEntity<Task>(taskService.getTaskById(id), HttpStatus.OK);
	}

	@PostMapping("/createTask")
	public ResponseEntity<String> createTask(@RequestBody TaskDTO taskDto) {
		return new ResponseEntity<String>(taskService.createTask(taskDto), HttpStatus.OK);
	}

	@PutMapping("/updateTask")
	public ResponseEntity<String> updateTask(@RequestParam String task_id, @RequestBody TaskDTO taskDTO) {
		return new ResponseEntity<String>(taskService.updateTask(task_id, taskDTO), HttpStatus.OK);
	}

	@PatchMapping("/updateStatus")
	public ResponseEntity<String> updateStatusOfTask(@RequestParam String task_id, @RequestParam Status status) {
		return new ResponseEntity<String>(taskService.updateStatusOfTask(task_id, status), HttpStatus.OK);
	}

	@PatchMapping("/updatePriority")
	public ResponseEntity<String> updatePriorityOfTask(@RequestParam String task_id, @RequestParam Priority priority) {
		return new ResponseEntity<String>(taskService.updatePriorityOfTask(task_id, priority), HttpStatus.OK);
	}

	@PatchMapping("/updateProject")
	public ResponseEntity<String> updateProjectOfTask(@RequestParam String task_id, @RequestParam String project_id) {
		return new ResponseEntity<String>(taskService.updateProjectOfTask(task_id, project_id), HttpStatus.OK);
	}

	@PatchMapping("/updateDepartment")
	public ResponseEntity<String> updateDepartmentOfTask(@RequestParam String task_id,
			@RequestParam Department department) {
		return new ResponseEntity<String>(taskService.updateDepartmentOfTask(task_id, department), HttpStatus.OK);
	}

	@DeleteMapping("/deleteTask")
	public ResponseEntity<String> deleteTaskById(@RequestParam String task_id) {
		return new ResponseEntity<String>(taskService.deleteTaskById(task_id), HttpStatus.OK);

	}

	@PostMapping("/uploadFile")
	public ResponseEntity<?> uploadSupportingDocuments(@RequestParam String task_id,
			@RequestParam(name = "file") MultipartFile[] multipartFiles) throws Exception {
		return new ResponseEntity<>(taskService.uploadSupportingDocuments(task_id, multipartFiles), HttpStatus.OK);
	}
	
	@GetMapping("/downloadFile")
	public ResponseEntity<?> downloadSupportingDocuments(@RequestParam String task_id) throws Exception{
		ByteArrayOutputStream byteArrayOutputStream = taskService.downloadSupportingDocuments(task_id);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=supporting_files.zip");
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
		
		 return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(byteArrayOutputStream.toByteArray());

	}
}
