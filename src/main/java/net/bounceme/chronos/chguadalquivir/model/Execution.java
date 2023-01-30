package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.Getter;

@Data
@Document(collection = "Executions")
public class Execution implements Serializable, Numerical {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641781709734044777L;
	
	@Id
    @Field("_id")
	private String id;

	private Integer value;
	
	private Long executionTime;
	
	@Getter(lazy=true) 
	private final Double number = (new BigDecimal(executionTime)).doubleValue();
}
