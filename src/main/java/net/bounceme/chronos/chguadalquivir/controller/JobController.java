package net.bounceme.chronos.chguadalquivir.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.BatchJobExecution;
import net.bounceme.chronos.chguadalquivir.model.Status;
import net.bounceme.chronos.chguadalquivir.model.Task;
import net.bounceme.chronos.chguadalquivir.services.JobService;

@RestController
@RequestMapping("/api")
@Slf4j
public class JobController {
	
	@Autowired
	private JobService jobService;

	/**
	 * @return
	 */
	@GetMapping("/")
	public String index() {
		return "Hello world";
	}

	/**
	 * @return
	 */
	@GetMapping("/status")
	public ResponseEntity<Status> status() {
		Status status = Status.builder().version(System.getProperty("java.version"))
				.platform(System.getProperty("os.name")).response("OK").build();
		
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
	
	@PostMapping("/execute")
	public ResponseEntity<Map<String, Object>> executeTask(@Valid @RequestBody Task task, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			if (result.hasErrors()) {
				List<String> errors = result.getFieldErrors().stream()
						.map(error -> String.format("El campo '%s' %s", error.getField(), error.getDefaultMessage()))
						.collect(Collectors.toList());
	
				response.put("errors", errors);
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			
			log.info("Ejecutar: {}", task.getName());
			jobService.run(task.getName());
			response.put("mensaje", "Tarea ejecutada correctamente");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/last")
	public ResponseEntity<Map<String, Object>> lastJob() {
		Map<String, Object> response = new HashMap<>();
		
		try {
			BatchJobExecution batchJobExecution = jobService.getLastJob();
			response.put("jobExecution", batchJobExecution);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
