package net.bounceme.chronos.chguadalquivir.model.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NotificacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9054030112050766235L;
	
	private String aplicacion;
	
	private Date fecha;
	
	private String mensaje;
}
