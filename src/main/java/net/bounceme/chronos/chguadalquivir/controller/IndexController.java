package net.bounceme.chronos.chguadalquivir.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.chguadalquivir.model.Status;

@RestController
@RequestMapping("/api")
public class IndexController {

	/**
	 * @return
	 */
	@GetMapping("/status")
	public ResponseEntity<Status> status() {
		Status status = Status.builder().version(System.getProperty("java.version"))
				.platform(System.getProperty("os.name")).response("OK").build();
		
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
}
