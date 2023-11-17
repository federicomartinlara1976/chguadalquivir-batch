package net.bounceme.chronos.chguadalquivir.model.batch;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="BATCH_STEP_EXECUTION")
@Data
public class BatchStepExecution implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5913365606887333291L;
	
	@Id
	@Column(name="STEP_EXECUTION_ID")
	private Long stepExecutionId;
	
	@Column(name="VERSION")
	private Integer version;
	
	@Column(name="STEP_NAME")
	private String stepName;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "JOB_EXECUTION_ID")
	private BatchJobExecution jobExecution;
	
	@OneToOne(mappedBy = "stepExecution")
	private BatchStepExecutionContext context;
	
	@Column(name="START_TIME")
	private Date startTime;
	
	@Column(name="END_TIME")
	private Date endTime;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="COMMIT_COUNT")
	private Integer commitCount;
	
	@Column(name="READ_COUNT")
	private Integer readCount;
	
	@Column(name="FILTER_COUNT")
	private Integer filterCount;
	
	@Column(name="WRITE_COUNT")
	private Integer writeCount;
	
	@Column(name="READ_SKIP_COUNT")
	private Integer readSkipCount;
	
	@Column(name="WRITE_SKIP_COUNT")
	private Integer writeSkipCount;
	
	@Column(name="PROCESS_SKIP_COUNT")
	private Integer processSkipCount;
	
	@Column(name="ROLLBACK_COUNT")
	private Integer rollbackCount;
	
	@Column(name="EXIT_CODE")
	private String exitCode;
	
	@Column(name="EXIT_MESSAGE")
	private String exitMessage;
	
	@Column(name="LAST_UPDATED")
	private Date lastUpdated;
}
