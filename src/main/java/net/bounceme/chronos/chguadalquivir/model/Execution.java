package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "Executions")
public class Execution implements Serializable, Numerical {

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
		return BigDecimal.valueOf(executionTime).doubleValue();
	}
}
