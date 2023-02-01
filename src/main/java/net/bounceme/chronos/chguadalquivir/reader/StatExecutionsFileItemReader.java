package net.bounceme.chronos.chguadalquivir.reader;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.reader.mapping.StatExecutionsLineMapper;

@Component
@Slf4j
public class StatExecutionsFileItemReader extends FlatFileItemReader<Execution> implements InitializingBean {

	@Autowired
	private Environment environment;

	@Autowired
	private SimpleDateFormat dateFormat;
	
	@Autowired
	private StatExecutionsLineMapper lineMapper;

	@Override
	public void afterPropertiesSet() {
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		try {
			String exportFilePath = environment.getRequiredProperty("application.lastExecutions.export.file.path");
			String suffix = environment.getRequiredProperty("application.lastExecutions.export.file.suffix");

			Date date = new Date();
			String sDate = dateFormat.format(date);
			Resource exportFileResource = new FileSystemResource(exportFilePath + "-" + sDate + "." + suffix);
			setResource(exportFileResource);
			
			//Set number of lines to skips. Use it if file has header rows.
			setLinesToSkip(1);   
			   
			//Configure how each line will be parsed and mapped to different values
			setLineMapper(lineMapper);

		} catch (Exception e) {
			log.error("ERROR: ", e);
		}
	}
}
