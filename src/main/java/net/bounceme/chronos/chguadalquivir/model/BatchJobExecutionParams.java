package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@JsonIgnore
	@Id
	@OneToOne
	@JoinColumn(name = "JOB_EXECUTION_ID")
	private BatchJobExecution jobExecution;
	
	@Column(name="TYPE_CD")
	private String typeCD;
	
	@Column(name="KEY_NAME")
	private String keyName;
	
	@Column(name="STRING_VAL")
	private String stringVal;
	
	@Column(name="DATE_VAL")
	private Date dateVal;
	
	@Column(name="LONG_VAL")
	private Long longVal;
	
	@Column(name="DOUBLE_VAL")
	private Double doubleVal;
	
	@Column(name="IDENTIFYING")
	private String identifying;
}
