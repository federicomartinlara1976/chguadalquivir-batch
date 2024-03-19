package net.bounceme.chronos.chguadalquivir.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;

@Component
public class CapacidadProcessor implements ItemProcessor<RegistroDiarioEmbalse, Embalse> {

	@Override
	public Embalse process(RegistroDiarioEmbalse item) throws Exception {
		Embalse embalse = Embalse.builder()
				.id(item.getCodigo())
				.nombre(item.getEmbalse())
				.capacidad(item.getCapacidad())
				.men(item.getMEN())
				.build();
		
		return embalse;
	}

}
