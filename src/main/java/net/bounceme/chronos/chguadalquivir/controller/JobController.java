package net.bounceme.chronos.chguadalquivir.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.chguadalquivir.model.Status;

@RestController
@RequestMapping("/api")
public class JobController {

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
	public Status status() {
		return Status.builder().version(System.getProperty("java.version"))
				.platform(System.getProperty("os.name")).response("OK").build();
	}
}
