package net.bounceme.chronos.chguadalquivir.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.Execution;

@Component
public class ExecutionProcessor implements ItemProcessor<Execution, String> {

	@Override
	public String process(Execution item) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
