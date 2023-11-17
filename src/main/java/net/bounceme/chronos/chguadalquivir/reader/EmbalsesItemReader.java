package net.bounceme.chronos.chguadalquivir.reader;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamSupport;
import org.springframework.beans.factory.annotation.Autowired;

import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.repository.EmbalseRepository;

public class EmbalsesItemReader extends ItemStreamSupport implements ItemReader<Embalse> {

	@Autowired
	private EmbalseRepository embalseRepository;

	private List<Embalse> records;

	private Integer index = 0;

	@Override
	public void open(ExecutionContext executionContext) {
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		records = embalseRepository.findAll();

		index = 0;
	}

	/**
	 *
	 */
	@Override
	public Embalse read() {
		Embalse nextElement = null;

		if (index < records.size()) {
			nextElement = records.get(index);
			index++;
		} else {
			index = 0;
		}

		return nextElement;
	}

}
