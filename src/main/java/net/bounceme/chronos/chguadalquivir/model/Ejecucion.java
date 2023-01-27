package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Execution")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Ejecucion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8674573146489840035L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer value;
	
	private Date initDate;
	
	private Long executionTime;
	
	private String taskName;
}