package net.bounceme.chronos.chguadalquivir.model;

import org.springframework.batch.core.ExitStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
public class ExecutionResult {
	
	private ExitStatus exitStatus;
	
	private String message;
}
