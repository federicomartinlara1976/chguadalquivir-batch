package net.bounceme.chronos.chguadalquivir.reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.PuntoControlElement;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioPluviometria;
import net.bounceme.chronos.chguadalquivir.reader.mapping.PuntoControlRowMapper;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;

@Component
@Slf4j
public class PluviometriaDailyRegisterItemReader extends ItemStreamSupport implements ItemReader<RegistroDiarioPluviometria> {

	@Value("${application.pluviometria.url}")
	private String url;

	@Autowired
	private CHGuadalquivirHelper helper;

	@Autowired
	private PuntoControlRowMapper elementMapper;

	private List<PuntoControlElement> records;

	private Integer index = 0;
	
	@Override
	public void open(ExecutionContext executionContext) {
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		try {
			records = new ArrayList<>();
	
			Document doc = helper.retrieveDocument(url);
			
			Elements elements = doc.select("table#ContentPlaceHolder1_GridLluviaTiempoReal > tbody > tr");
	
			for (int i = 1; i < elements.size(); i++) {
				PuntoControlElement pe = PuntoControlElement.builder().element(elements.get(i)).build();
					
				records.add(pe);
			}
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage());
			records = Collections.emptyList();
		}
		
		index = 0;
	}

	/**
	 *
	 */
	@Override
	public RegistroDiarioPluviometria read() {
		RegistroDiarioPluviometria nextElement = null;

		if (index < records.size()) {
			PuntoControlElement pe = records.get(index);
			
			nextElement = elementMapper.map(pe);
			log.info("{}", nextElement.toString());
			
			index++;
		} else {
			index = 0;
		}

		return nextElement;
	}

}
