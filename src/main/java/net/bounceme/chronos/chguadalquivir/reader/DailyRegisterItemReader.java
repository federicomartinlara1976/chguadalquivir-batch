package net.bounceme.chronos.chguadalquivir.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.model.Zona;
import net.bounceme.chronos.chguadalquivir.model.ZonaElement;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;
import net.bounceme.chronos.chguadalquivir.support.ElementMapper;

@Slf4j
public class DailyRegisterItemReader implements ItemReader<Embalse>, InitializingBean {

	@Value("${application.importJob.url}")
	private String url;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private CHGuadalquivirHelper helper;

	@Getter
	@Setter
	private ElementMapper<Embalse> elementMapper;

	private List<ZonaElement> records;

	private Integer index = 0;

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(url, "Must provide the url");
		Assert.notNull(elementMapper, "Must provide a elementMapper");

		initialize();
	}

	/**
	 * 
	 */
	@SneakyThrows
	private void initialize() {

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

			for (int i = 1; i < elements.size(); i++) {
				ZonaElement ze = ZonaElement.builder().zona(zona).element(elements.get(i)).build();
				
				records.add(ze);
			}
		}

		index = 0;
	}

	/**
	 *
	 */
	@Override
	public Embalse read() {
		Embalse nextElement = null;

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
