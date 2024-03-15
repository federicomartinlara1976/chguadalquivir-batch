package net.bounceme.chronos.chguadalquivir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude={BatchAutoConfiguration.class})
@ImportResource({
    "classpath:applicationContext.xml",
	"classpath:importJob.xml",
	"classpath:importZonas.xml",
	"classpath:importEmbalses.xml",
	"classpath:importDiario.xml",
	"classpath:updateCapacidad.xml",
	"classpath:flow.import.xml",
	"classpath:flow.zonas.xml",
	"classpath:flow.embalses.xml",
	"classpath:flow.diario.xml",
	"classpath:flow.updateCapacidad.xml"
})
public class ChGuadalquivirApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChGuadalquivirApplication.class, args);
	}
}
