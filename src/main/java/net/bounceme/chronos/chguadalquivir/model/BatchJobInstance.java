package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="BATCH_JOB_INSTANCE")
@Data
public class BatchJobInstance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5296708324116403600L;
	
	@Id
	@Column(name="JOB_INSTANCE_ID")
	private Long jobInstanceId;

	@Column(name="VERSION")
	private Integer version;
	
	@Column(name="JOB_NAME")
	private String jobName;
	
	@Column(name="JOB_KEY")
	private String jobJey;
}
