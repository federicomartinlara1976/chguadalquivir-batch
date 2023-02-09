package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="BATCH_JOB_EXECUTION")
@Getter
@Setter
@ToString
public class BatchJobExecution implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2151133111044402554L;
	
	@Id
	@Column(name="JOB_EXECUTION_ID")
	private Long jobExecutionId;
	
	@Column(name="VERSION")
	private Integer version;

	@OneToOne @MapsId
	private BatchJobInstance jobInstance;
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="START_TIME")
	private Date startTime;
	
	@Column(name="END_TIME")
	private Date endTime;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="EXIT_CODE")
	private String exitCode;
	
	@Column(name="EXIT_MESSAGE")
	private String exitMessage;
	
	@Column(name="LAST_UPDATED")
	private Date lastUpdated;
	
	@Column(name="JOB_CONFIGURATION_LOCATION")
	private String jobConfigurationLocation;
}
