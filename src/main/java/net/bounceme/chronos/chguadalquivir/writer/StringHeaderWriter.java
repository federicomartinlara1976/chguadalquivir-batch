package net.bounceme.chronos.chguadalquivir.writer;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

public class StringHeaderWriter implements FlatFileHeaderCallback {
	
	private String header;
	
	/**
	 * @param header
	 */
	public StringHeaderWriter(String header) {
		this.header = header;
	}

	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write(header);
	}

}
