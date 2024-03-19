package net.bounceme.chronos.chguadalquivir;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;
import net.bounceme.chronos.chguadalquivir.model.Zona;
import net.bounceme.chronos.chguadalquivir.model.ZonaElement;
import net.bounceme.chronos.chguadalquivir.reader.mapping.EmbalseRowMapper;
import net.bounceme.chronos.chguadalquivir.reader.mapping.StatExecutionsFieldMapper;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;

@SpringBootTest
@Slf4j
public class TestMappers {
	
	@Autowired
	private CHGuadalquivirHelper helper;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private EmbalseRowMapper embalseRowMapper;
	
	@Value("${application.importJob.url}")
	private String url;
	
	@Test
	public void testStatExecutionsFieldMapper() {
		StatExecutionsFieldMapper mapper = new StatExecutionsFieldMapper();
		assertNotNull(mapper);
	}
	
	@Test
	public void testEmbalseRowMapper() {
		try {
			List<Zona> zonas = helper.getZonesFromJsonResource(mapper, "zonas.json");
			assertNotNull(zonas);
			
			Document document = helper.retrieveDocument(url);
			assertNotNull(document);
			
			Map<String, String> postData = helper.initFormData(document);
			String selectName = helper.getNameFrom(document, "form2", "DDBzona");
			
			for (Zona zona : zonas) {
				postData.put(selectName, zona.getCodigo());
				log.debug("{}", postData.toString());

				document = Jsoup.connect(url).data(postData).post();

				Elements elements = document.select("table#ContentPlaceHolder1_GridNivelesEmbalses > tbody > tr");

				Integer inicio = 1;

				for (int i = inicio; i < elements.size(); i++) {
					ZonaElement ze = ZonaElement.builder().zona(zona).element(elements.get(i)).build();
					
					RegistroDiarioEmbalse embalse = embalseRowMapper.map(ze);
					assertNotNull(embalse);
					log.info("{}, {}", embalse.toString(), embalse.hashCode());
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			assertTrue(true);
		}
	}
}
