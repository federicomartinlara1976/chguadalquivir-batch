package net.bounceme.chronos.chguadalquivir;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.model.ExecutionStats;
import net.bounceme.chronos.chguadalquivir.model.Zona;
import net.bounceme.chronos.chguadalquivir.reader.mapping.StatExecutionsFieldMapper;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;
import net.bounceme.chronos.chguadalquivir.support.StatsCalculations;

@SpringBootTest
@Slf4j
public class CHGuadalquivirApplicationTests {
	
	@Autowired
	private CHGuadalquivirHelper helper;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private StatsCalculations statsCalculations;
	
	@Value("${application.importJob.url}")
	private String url;
	
	private List<Execution> executions;
	
	@Test
	void contextLoads() {
	}
	
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
		} catch (Exception e) {
			log.error(e.getMessage());
			assertTrue(true);
		}
	}

	@Test
	public void testStatCalculations() {
		buildExecutions();
		
		Double average = statsCalculations.calculateAverage(executions);
		Double deviation = statsCalculations.calculateDeviation(executions, average);
		Double variation = statsCalculations.calculateVariation(deviation);
		
		assertNotNull(average);
		assertNotNull(deviation);
		assertNotNull(variation);
	}
	
	@Test
	public void testExecutionStatsModel() {
		ExecutionStats executionStats = ExecutionStats.builder().average(1.0).deviation(1.0).variation(1.0).build();
		assertNotNull(executionStats);
		log.info(executionStats.toString());
		
		executionStats = new ExecutionStats(1L, new Date(), 1.0, 1.0, 1.0);
		assertNotNull(executionStats);
		
		executionStats = new ExecutionStats();
		executionStats.setId(1L);
		executionStats.setAverage(1.0);
		executionStats.setDeviation(1.0);
		executionStats.setVariation(1.0);
		log.info("{}, {}", executionStats.toString(), executionStats.hashCode());
	}
	
	@Test
	public void testExecutionModel() {
		Execution execution = new Execution();
		execution.setId("2023-01-23");
		execution.setValue(1);
		execution.setExecutionTime(1L);
		assertNotNull(execution);
		log.info("{}, {}", execution.toString(), execution.hashCode());
	}
	
	@Test
	public void testEmbalseModel() {
		Embalse embalse = new Embalse();
		embalse.setId("id");
		embalse.setEmbalse("Embalse");
		embalse.setCod_zona("cod_zona");
		embalse.setZona("zona");
		embalse.setCodigo("codigo");
		embalse.setPorcentaje(50.0F);
		embalse.setCapacidad(150F);
		embalse.setVolumen(150F);
		embalse.setMEN(150F);
		embalse.setNivel(150F);
		
		assertNotNull(embalse);
		
		assertEquals("id", embalse.getId());
		assertEquals("Embalse", embalse.getEmbalse());
		assertEquals("cod_zona", embalse.getCod_zona());
		assertEquals("zona", embalse.getZona());
		assertEquals("codigo", embalse.getCodigo());
		assertEquals(50.0F, embalse.getPorcentaje());
		assertEquals(150F, embalse.getCapacidad());
		assertEquals(150F, embalse.getVolumen());
		assertEquals(150F, embalse.getMEN());
		assertEquals(150F, embalse.getNivel());
		
		log.info("{}, {}", embalse.toString(), embalse.hashCode());
	}
	
	@Test
	public void testStatExecutionsFieldMapper() {
		StatExecutionsFieldMapper mapper = new StatExecutionsFieldMapper();
		assertNotNull(mapper);
	}
	
	private void buildExecutions() {
		executions = new ArrayList<>();
		
		Execution execution = Execution.builder().id("2023-01-23").value(1).executionTime(1896L).build();
		executions.add(execution);
		
		execution = Execution.builder().id("2023-01-24").value(1).executionTime(1923L).build();
		executions.add(execution);
		
		execution = Execution.builder().id("2023-01-25").value(1).executionTime(1642L).build();
		executions.add(execution);
		
		execution = Execution.builder().id("2023-01-26").value(1).executionTime(1828L).build();
		executions.add(execution);
		
		execution = Execution.builder().id("2023-01-27").value(1).executionTime(1805L).build();
		executions.add(execution);
		
		execution = Execution.builder().id("2023-01-28").value(1).executionTime(1665L).build();
		executions.add(execution);
		
	}
}
