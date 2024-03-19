package net.bounceme.chronos.chguadalquivir.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.Zona;

@Component
public class ZonasProcessor implements ItemProcessor<Zona, Zona> {

	@Override
	public Zona process(Zona item) throws Exception {
		return item;
	}

}
