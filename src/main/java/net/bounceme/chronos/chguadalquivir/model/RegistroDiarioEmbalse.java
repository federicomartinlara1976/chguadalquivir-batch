package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Document(collection = "#{@repositoryCollectionCustom.getCollectionName()}")
@ToString
@Data
public class RegistroDiarioEmbalse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641781709734044777L;
	
	@Id
    @Field("_id")
	private String id;

	@Field("Embalse")
	private String embalse;
	
	@Field("cod_zona")
	private String codZona;
	
	private String zona;
	
	private String codigo;
	
	@NotNull
	private Float porcentaje;
	
	@Field("Capacidad")
	@NotNull
	private Float capacidad;
	
	@Field("Volumen")
	@NotNull
	private Float volumen;
	
	@Field("MEN")
	@NotNull
	private Float men;
	
	@Field("Nivel")
	@NotNull
	private Float nivel;
	
	@Transient
	private Date fecha;
}
