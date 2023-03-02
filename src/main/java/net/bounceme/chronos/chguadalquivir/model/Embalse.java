package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "#{@repositoryCollectionCustom.getCollectionName()}")
@ToString
public class Embalse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641781709734044777L;
	
	@Id
    @Field("_id")
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String Embalse;
	
	@Getter
	@Setter
	private String cod_zona;
	
	@Getter
	@Setter
	private String zona;
	
	@Getter
	@Setter
	private String codigo;
	
	@NotNull
	@Getter
	@Setter
	private Float porcentaje;
	
	@NotNull
	@Getter
	@Setter
	private Float Capacidad;
	
	@NotNull
	@Getter
	@Setter
	private Float Volumen;
	
	@NotNull
	@Getter
	@Setter
	private Float MEN;
	
	@NotNull
	@Getter
	@Setter
	private Float Nivel;
	
	@Transient
	@Getter
	@Setter
	private Date fecha;
}
