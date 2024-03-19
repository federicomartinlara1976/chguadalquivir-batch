package net.bounceme.chronos.chguadalquivir.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "zona")
@Data
public class ZonaJpa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5312925342306539860L;
	
	@Id
	@Column(name = "codigo")
	private String codigo;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;

}
