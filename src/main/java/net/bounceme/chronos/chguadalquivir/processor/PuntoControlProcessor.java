package net.bounceme.chronos.chguadalquivir.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.PuntoControl;

@Component
public class PuntoControlProcessor implements ItemProcessor<PuntoControl, PuntoControl> {

	@Override
	public PuntoControl process(PuntoControl item) throws Exception {
		return item;
	}

}
