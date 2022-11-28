package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "#{@repositoryCollectionCustom.getCollectionName()}")
public class Embalse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641781709734044777L;
	
	@Id
    @Field("_id")
	private String id;

	private String Embalse;
	private Float porcentaje;
	private String cod_zona;
	private String zona;
	private String codigo;
	private Float Capacidad;
	private Float Volumen;
	private Float MEN;
	private Float Nivel;
	
	@Transient
	private Date fecha;
}
