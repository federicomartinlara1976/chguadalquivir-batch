package net.bounceme.chronos.chguadalquivir.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BPMConfigurations {
	
	//lives on classpath -- src/main/resources/rules/*.drl 
	//private static final String[] drlFiles = { "rules/discountRules.drl" };
 
//	@Bean
//	KieContainer kieContainer() {
//		System.setProperty("drools.dialect.mvel.strict", "false"); // Desactiva verificaciones obsoletas
//		KieServices kieServices = KieServices.Factory.get(); 
//		//Load Rules and Ecosystem Definitions
//		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
// 
//		for (String ruleFile : drlFiles) {
//			kieFileSystem.write(ResourceFactory.newClassPathResource(ruleFile));
//		}
// 
//		//Generate Modules and all internal Structures
//		KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
//		kieBuilder.buildAll();
//		KieModule kieModule = kieBuilder.getKieModule();
//		
//		return kieServices.newKieContainer(kieModule.getReleaseId());
//	}
	
	@Bean
	KieContainer kieContainer() {
	    System.setProperty("drools.dialect.mvel.strict", "false"); // Desactiva verificaciones obsoletas
	    KieServices kieServices = KieServices.Factory.get();
	    KieContainer kieContainer = kieServices.getKieClasspathContainer();
	    return kieContainer;
	}
}