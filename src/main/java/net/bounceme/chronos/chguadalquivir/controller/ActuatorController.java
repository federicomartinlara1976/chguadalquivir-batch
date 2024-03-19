package net.bounceme.chronos.chguadalquivir.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore // Exclude this controller from Swagger documentation
public class ActuatorController {

    @GetMapping("/actuator/health")
    public String health() {
        return "Actuator health endpoint";
    }

    @GetMapping("/actuator/info")
    public String info() {
        return "";
    }
}