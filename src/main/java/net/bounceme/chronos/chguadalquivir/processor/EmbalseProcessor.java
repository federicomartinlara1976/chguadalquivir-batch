package net.bounceme.chronos.chguadalquivir.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.Embalse;

@Component
public class EmbalseProcessor implements ItemProcessor<Embalse, Embalse> {

	@Override
	public Embalse process(Embalse item) throws Exception {
		return item;
	}

}
