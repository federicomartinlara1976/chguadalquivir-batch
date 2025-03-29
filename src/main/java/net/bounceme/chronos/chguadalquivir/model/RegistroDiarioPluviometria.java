package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;
import java.util.Date;

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

	private String nombre;
	
	private String zona;
	
	private String codigo;
	
	private Float horaActual;
	
	private Float horaAnterior;
	
	private Float ultimas12Horas;
	
	private Float hoy;
	
	private Float ayer;
	
	private String unidadMedida;
	
	@Transient
	private Date fecha;
}
