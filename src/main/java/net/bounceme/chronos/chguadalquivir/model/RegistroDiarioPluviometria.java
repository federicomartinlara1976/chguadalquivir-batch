package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.ToString;

@Document(collection = "#{@repositoryCollectionCustom.getCollectionName()}")
@ToString
@Data
public class RegistroDiarioPluviometria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641781709734044777L;
	
	@Id
    @Field("_id")
	private String id;

	private String Embalse;
	
	private String cod_zona;
	
	private String zona;
	
	private String codigo;
	
	@NotNull
	private Float porcentaje;
	
	@NotNull
	private Float Capacidad;
	
	@NotNull
	private Float Volumen;
	
	@NotNull
	private Float MEN;
	
	@NotNull
	private Float Nivel;
	
	@Transient
	private Date fecha;
}
