package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "Executions")
public class Execution extends Numerical implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641781709734044777L;
	
	@Id
    @Field("_id")
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private Integer value;
	
	@Getter
	@Setter
	private Long executionTime;
	
	public Double getNumber() {
		return (new BigDecimal(executionTime)).doubleValue();
	}
}
