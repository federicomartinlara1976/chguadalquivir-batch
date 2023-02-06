package net.bounceme.chronos.chguadalquivir.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Status;
import net.bounceme.chronos.chguadalquivir.model.Task;

@RestController
@RequestMapping("/api")
@Slf4j
public class JobController {
	
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobExplorer jobExplorer;

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
			run(task.getName());
			response.put("mensaje", "Tarea ejecutada correctamente");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/last")
	public ResponseEntity<Map<String, Object>> lastJob(@Valid @RequestBody Task task, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			if (result.hasErrors()) {
				List<String> errors = result.getFieldErrors().stream()
						.map(error -> String.format("El campo '%s' %s", error.getField(), error.getDefaultMessage()))
						.collect(Collectors.toList());
	
				response.put("errors", errors);
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			
			log.info("Buscar: {}", task.getName());
			JobInstance jobInstance = getLastJobInstance(task.getName());
			response.put("jobInstance", jobInstance);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * @param name
	 * @throws Exception
	 */
	private void run(String name) throws Exception {
		try {
			JobParametersBuilder builder = new JobParametersBuilder();
			builder.addDate("date", new Date());
			
			Job job = ctx.getBean(name, Job.class);
			JobExecution result = jobLauncher.run(job, builder.toJobParameters());
			
			// Exit on failure
			if (ExitStatus.FAILED.equals(result.getExitStatus())) {
				throw new Exception("La tarea ha fallado");
			}
		} catch (NoSuchBeanDefinitionException e) {
			throw new Exception("Tarea no encontrada");
		}
	}
	
	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	private JobInstance getLastJobInstance(String name) {
		List<JobInstance> jobInstances = jobExplorer.getJobInstances(name, 0, 10);
		
		return (CollectionUtils.isNotEmpty(jobInstances)) ? jobInstances.get(0) : null;
	}
}
