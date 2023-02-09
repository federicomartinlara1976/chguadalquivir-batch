package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="BATCH_JOB_EXECUTION_PARAMS")
@Getter
@Setter
@ToString
public class BatchJobExecutionParams implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5130311799595596226L;

}
