package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class ExecutionStats implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8674573146489840035L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date initDate;
	
	private Double average;
	
	private Double deviation;
	
	private Double variation;
}
