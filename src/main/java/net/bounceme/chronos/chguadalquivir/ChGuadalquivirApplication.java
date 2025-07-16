package net.bounceme.chronos.chguadalquivir;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.notifications.services.NotificationService;

@SpringBootApplication(exclude={BatchAutoConfiguration.class})
@ImportResource({
    "classpath:applicationContext.xml",
	"classpath:importJob.xml",
	"classpath:importZonas.xml",
	"classpath:importEmbalses.xml",
	"classpath:importPuntosControl.xml",
	"classpath:importDiario.xml",
	"classpath:updateCapacidad.xml",
	"classpath:flow.import.xml",
	"classpath:flow.zonas.xml",
	"classpath:flow.embalses.xml",
	"classpath:flow.diario.xml",
	"classpath:flow.updateCapacidad.xml",
	"classpath:flow.pluviometria.xml",
	"classpath:flow.puntoscontrol.xml"
})
@Slf4j
public class ChGuadalquivirApplication implements CommandLineRunner {

	private NotificationService notificationService;

	public ChGuadalquivirApplication(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(ChGuadalquivirApplication.class);
		builder.headless(false);
		builder.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		String version = System.getProperty("java.version");
		log.debug("Java version: {}", version);
		notificationService.sendNotification("chguadalquivir-batch", "Proceso automatizado iniciado correctamente", "OK");
	}
}
