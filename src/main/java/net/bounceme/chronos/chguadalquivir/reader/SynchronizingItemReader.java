package net.bounceme.chronos.chguadalquivir.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import net.bounceme.chronos.chguadalquivir.model.Embalse;

public class SynchronizingItemReader implements ItemReader<Embalse>, ItemStream {
	
	@Autowired
	private DailyRegisterItemReader delegate;

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		if (delegate instanceof ItemStream) {
			((ItemStream) delegate).open(executionContext);
		}
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		if (delegate instanceof ItemStream) {
			((ItemStream) delegate).update(executionContext);
		}
	}

	@Override
	public void close() throws ItemStreamException {
		if (delegate instanceof ItemStream) {
			((ItemStream) delegate).close();
		}
	}

	@Override
	public synchronized  Embalse read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return delegate.read();
	}

}
