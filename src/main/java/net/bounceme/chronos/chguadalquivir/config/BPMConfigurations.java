package net.bounceme.chronos.chguadalquivir.config;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BPMConfigurations {
	
	@Bean
	KieContainer kieContainer() {
	    System.setProperty("drools.dialect.mvel.strict", "false"); // Desactiva verificaciones obsoletas
	    KieServices kieServices = KieServices.Factory.get();
	    KieContainer kieContainer = kieServices.getKieClasspathContainer();
	    return kieContainer;
	}
}