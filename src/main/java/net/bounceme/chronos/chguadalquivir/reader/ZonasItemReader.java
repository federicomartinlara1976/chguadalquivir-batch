package net.bounceme.chronos.chguadalquivir.reader;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamSupport;
import org.springframework.beans.factory.annotation.Autowired;

import net.bounceme.chronos.chguadalquivir.model.Zona;
import net.bounceme.chronos.chguadalquivir.repository.ZonasRepository;

public class ZonasItemReader extends ItemStreamSupport implements ItemReader<Zona> {

	@Autowired
	private ZonasRepository zonasRepository;

	private List<Zona> records;

	private Integer index = 0;

	@Override
	public void open(ExecutionContext executionContext) {
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		records = zonasRepository.findAll();

		index = 0;
	}

	/**
	 *
	 */
	@Override
	public Zona read() {
		Zona nextElement = null;

		if (index < records.size()) {
			nextElement = records.get(index);
			index++;
		} else {
			index = 0;
		}

		return nextElement;
	}

}
