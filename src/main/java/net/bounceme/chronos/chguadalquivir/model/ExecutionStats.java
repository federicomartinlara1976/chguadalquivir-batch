package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ExecutionStats implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8674573146489840035L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	private Date initDate;
	
	@Getter
	@Setter
	private Double average;
	
	@Getter
	@Setter
	private Double deviation;
	
	@Getter
	@Setter
	private Double variation;
}
