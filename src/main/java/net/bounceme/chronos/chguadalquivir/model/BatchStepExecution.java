package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="BATCH_STEP_EXECUTION")
@Getter
@Setter
@ToString
public class BatchStepExecution implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5913365606887333291L;

}
