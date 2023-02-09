package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="BATCH_STEP_EXECUTION_CONTEXT")
@Getter
@Setter
@ToString
public class BatchStepExecutionContext implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 458065714471650814L;
	
	@Column(name="SHORT_CONTEXT")
	private String shortContext;
	
	@Column(name="SERIALIZED_CONTEXT")
	private String serializedContext;

}
