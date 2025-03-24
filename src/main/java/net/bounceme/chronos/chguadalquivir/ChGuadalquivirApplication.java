package net.bounceme.chronos.chguadalquivir;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

import net.bounceme.chronos.notifications.services.NotificationService;

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
	"classpath:flow.updateCapacidad.xml",
	"classpath:flow.pluviometria.xml"
})
public class ChGuadalquivirApplication implements CommandLineRunner {

	@Autowired
	private NotificationService notificationService;

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(ChGuadalquivirApplication.class);
		builder.headless(false);
		builder.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		notificationService.sendNotification("chguadalquivir-batch", "Proceso automatizado iniciado correctamente", "OK");
	}
}
