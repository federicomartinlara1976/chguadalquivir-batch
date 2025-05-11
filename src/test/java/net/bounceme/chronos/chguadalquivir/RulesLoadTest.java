package net.bounceme.chronos.chguadalquivir;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RulesLoadTest {

    @Autowired
    private KieContainer kieContainer;

    @Test
    public void testCargaReglas() {
        assertNotNull(kieContainer.getKieBase("RulesKB"));
    }
}