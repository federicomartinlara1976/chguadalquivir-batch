package net.bounceme.chronos.chguadalquivir;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;
import net.bounceme.chronos.chguadalquivir.support.Constants;

@SpringBootTest
public class ApplicationTests {
	
	@Autowired
	private CHGuadalquivirHelper helper;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void testConstants() {
		Constants constants = new Constants();
		
		assertNotNull(constants);
	}
	
	@Test
	void testRound() {
		Double rounded = helper.round(1.2345, 2);
		assertNotNull(rounded);
	}
}
