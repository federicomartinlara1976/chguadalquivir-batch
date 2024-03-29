package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Document(collection = "Embalses")
@ToString
@Data
@Builder
public class Embalse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641781709734044777L;
	
	@Id
    @Field("_id")
	private String id;

	private String nombre;
	
	private Float capacidad;
	
	private Float men;
}
