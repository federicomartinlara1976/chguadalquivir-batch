package net.bounceme.chronos.chguadalquivir.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector.SelectorParseException;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Zona;
import net.bounceme.chronos.chguadalquivir.model.ZonaElement;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;
import net.bounceme.chronos.chguadalquivir.support.ElementMapper;

@Slf4j
public class DailyRegisterItemReader<T> implements ItemReader<T>, InitializingBean {

	@Value("${application.importJob.url}")
	private String url;

	@Value("${application.importJob.excludeHeader}")
	private Boolean excludeHeader;

	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private CHGuadalquivirHelper helper;

	@Getter
	@Setter
	private ElementMapper<T> elementMapper;

	private List<ZonaElement> records;

	private Integer index = 0;

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(url, "Must provide the url");
		Assert.notNull(excludeHeader, "excludeHeader must be [true/false]");
		Assert.notNull(elementMapper, "Must provide a elementMapper");

		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		try {
			List<Zona> zonas = helper.getZonesFromJsonResource(mapper, "zonas.json");
			records = new ArrayList<>();

			Document doc = helper.retrieveDocument(url);
			Map<String, String> postData = helper.initFormData(doc);
			String selectName = helper.getNameFrom(doc, "form2", "DDBzona");

			for (Zona zona : zonas) {
				postData.put(selectName, zona.getCodigo());
				log.debug("{}", postData.toString());

				doc = Jsoup.connect(url).data(postData).post();

				Elements elements = doc.select("table#ContentPlaceHolder1_GridNivelesEmbalses > tbody > tr");

				Integer inicio = (excludeHeader) ? 1 : 0;

				for (int i = inicio; i < elements.size(); i++) {
					ZonaElement ze = new ZonaElement();
					ze.setZona(zona);
					ze.setElement(elements.get(i));
					
					records.add(ze);
				}
			}

			index = 0;
		} catch (Exception e) {
			log.error("ERROR: ", e);
		}
	}

	/**
	 *
	 */
	@Override
	public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException,
			SelectorParseException {
		T nextElement = null;

		if (index < records.size()) {
			ZonaElement ze = records.get(index);
			nextElement = elementMapper.map(ze);
			index++;
		} else {
			index = 0;
		}

		return nextElement;
	}

}
