package net.bounceme.chronos.chguadalquivir.processor;

import java.util.Date;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.services.RegistroService;

@Component
@Slf4j
public class CapacidadProcessor implements ItemProcessor<Embalse, Embalse> {
	
	@Autowired
	private RegistroService registroService;

	@Override
	public Embalse process(Embalse item) throws Exception {
		Float capacidad = registroService.getCapacidad(item.getId(), new Date());
		log.info("Embalse: {}, capacidad: {}", item.getNombre(), capacidad);
		
		item.setCapacidad(capacidad);
		
		
		return item;
	}

}
