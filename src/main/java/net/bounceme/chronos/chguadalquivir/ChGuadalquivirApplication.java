package net.bounceme.chronos.chguadalquivir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude={BatchAutoConfiguration.class})
@ImportResource({
    "classpath:applicationContext.xml",
	"classpath:importJob.xml"
})
public class ChGuadalquivirApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChGuadalquivirApplication.class, args);
	}
}
