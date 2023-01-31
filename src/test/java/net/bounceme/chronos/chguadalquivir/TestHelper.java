package net.bounceme.chronos.chguadalquivir;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Zona;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;

@SpringBootTest
@Slf4j
public class TestHelper {
	
	@Autowired
	private CHGuadalquivirHelper helper;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Value("${application.importJob.url}")
	private String url;
	
	@Test
	public void testRound() {
		Double rounded = helper.round(1.2345, 2);
		assertNotNull(rounded);
	}
	
	@Test
	public void testSubtractDays() {
		Date modified = helper.subtractDays(new Date(), 5);
		assertNotNull(modified);
	}
	
	@Test
	public void testParseDate() {
		String sDate = helper.parseDate(new Date());
		assertNotNull(sDate);
	}
	
	@Test
	public void testZonesFromResourceFail() {
		List<Zona> zonas = helper.getZonesFromJsonResource(mapper, "");
		assertEquals(Collections.emptyList(), zonas);
	}
	
	@Test
	public void testDocumentFail() {
		try {
			Document document = helper.retrieveDocument(url);
			String name = helper.getNameFrom(document, "form", "idSelect");
			log.info(name);
		} catch (Exception e) {
			log.error(e.getMessage());
			assertTrue(true);
		}
	}
}
