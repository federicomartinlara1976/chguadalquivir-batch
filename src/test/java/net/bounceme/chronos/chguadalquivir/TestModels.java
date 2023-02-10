package net.bounceme.chronos.chguadalquivir;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.model.ExecutionStats;
import net.bounceme.chronos.chguadalquivir.model.Status;
import net.bounceme.chronos.chguadalquivir.model.Task;
import net.bounceme.chronos.chguadalquivir.model.Zona;
import net.bounceme.chronos.chguadalquivir.model.ZonaElement;

@SpringBootTest
@Slf4j
public class TestModels {

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

		Date d = new Date();
		executionStats.setInitDate(d);

		assertEquals(1L, executionStats.getId());
		assertEquals(1.0, executionStats.getAverage());
		assertEquals(1.0, executionStats.getDeviation());
		assertEquals(1.0, executionStats.getVariation());
		assertEquals(d, executionStats.getInitDate());

		log.info("{}, {}", executionStats.toString(), executionStats.hashCode());
	}

	@Test
	public void testExecutionModel() {
		Execution execution = new Execution();
		execution.setId("2023-01-23");
		execution.setValue(1);
		execution.setExecutionTime(1L);
		assertNotNull(execution);

		assertEquals("2023-01-23", execution.getId());
		assertEquals(1, execution.getValue());
		assertEquals(1L, execution.getExecutionTime());
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

		Date d = new Date();
		embalse.setFecha(d);

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
		assertEquals(d, embalse.getFecha());

		log.info("{}, {}", embalse.toString(), embalse.hashCode());
	}

	@Test
	public void testZonaModel() {
		Zona zona = new Zona();
		zona.setCodigo("codigo");
		zona.setDescripcion("descripcion");
		zona.setNombre("nombre");

		assertNotNull(zona);
		assertEquals("codigo", zona.getCodigo());
		assertEquals("descripcion", zona.getDescripcion());
		assertEquals("nombre", zona.getNombre());

		zona = Zona.builder().codigo("codigo").descripcion("descripcion").nombre("nombre").build();
		assertNotNull(zona);

		zona = new Zona("codigo", "descripcion", "nombre");
		assertNotNull(zona);
		log.info("{}, {}", zona.toString(), zona.hashCode());
	}

	@Test
	public void testZonaElementModel() {
		Zona zona = new Zona();
		assertNotNull(zona);

		Element element = new Element("<hr/>");
		ZonaElement ze = ZonaElement.builder().zona(zona).element(element).build();

		assertEquals(zona, ze.getZona());
		assertEquals(element, ze.getElement());
		log.info("{}, {}", ze.toString(), ze.hashCode());
	}

	@Test
	public void testStatus() {
		Status status = Status.builder().version(System.getProperty("java.version"))
				.platform(System.getProperty("os.name")).response("OK").build();
		assertNotNull(status);
		
		assertEquals(System.getProperty("java.version"), status.getVersion());
		assertEquals(System.getProperty("os.name"), status.getPlatform());
		assertEquals("OK", status.getResponse());
		
		status = new Status(System.getProperty("java.version"), System.getProperty("os.name"), "OK");
		assertNotNull(status);
		
		assertEquals(System.getProperty("java.version"), status.getVersion());
		assertEquals(System.getProperty("os.name"), status.getPlatform());
		assertEquals("OK", status.getResponse());
		
		status = new Status();
		status.setVersion(System.getProperty("java.version"));
		status.setPlatform(System.getProperty("os.name"));
		status.setResponse("OK");
		
		assertNotNull(status);
		
		assertEquals(System.getProperty("java.version"), status.getVersion());
		assertEquals(System.getProperty("os.name"), status.getPlatform());
		assertEquals("OK", status.getResponse());
	}
	
	@Test
	public void testTask() {
		Task task = Task.builder().name("task").build();
		assertNotNull(task);
		
		assertEquals("task", task.getName());
		
		task = new Task("task");
		assertNotNull(task);
		
		assertEquals("task", task.getName());
		
		task = new Task();
		task.setName("task");
		
		assertNotNull(task);
		
		assertEquals("task", task.getName());
	}
}
