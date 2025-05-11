package net.bounceme.chronos.chguadalquivir.services;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.dto.ProductPrice;

@Service
@Slf4j
public class PriceCalculatorService {

	@Autowired
	private KieContainer kieContainer;

	public void executeRules(ProductPrice productPrice) {
		log.info("Start calculating process");
		
		KieSession kieSession = kieContainer.newKieSession("RulesKS");
		kieSession.insert(productPrice);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		log.info("Calculating process finished");
	}
}
