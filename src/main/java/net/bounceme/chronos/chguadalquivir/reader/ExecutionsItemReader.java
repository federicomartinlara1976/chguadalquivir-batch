package net.bounceme.chronos.chguadalquivir.reader;

import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.repository.ExecutionsRepository;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;

@Component
public class ExecutionsItemReader implements ItemReader<Execution>, InitializingBean {

	@Autowired
	private CHGuadalquivirHelper helper;

	@Autowired
	private ExecutionsRepository executionsRepository;

	private List<Execution> records;

	private Integer index = 0;

	@Override
	public void afterPropertiesSet() {
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		Date currentDate = new Date();
		Date beforeDate = helper.subtractDays(currentDate, 5);

		String from = helper.parseDate(beforeDate);
		String to = helper.parseDate(currentDate);

		records = executionsRepository.listExecutions(from, to);

		index = 0;
	}

	/**
	 *
	 */
	@Override
	public Execution read() {
		Execution nextElement = null;

		if (index < records.size()) {
			nextElement = records.get(index);
			index++;
		} else {
			index = 0;
		}

		return nextElement;
	}

}
