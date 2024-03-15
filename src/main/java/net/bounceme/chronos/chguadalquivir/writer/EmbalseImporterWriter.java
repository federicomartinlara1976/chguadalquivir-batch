package net.bounceme.chronos.chguadalquivir.writer;

import java.util.List;
import java.util.Objects;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.model.jpa.EmbalseJpa;
import net.bounceme.chronos.chguadalquivir.model.jpa.ZonaJpa;
import net.bounceme.chronos.chguadalquivir.services.EmbalseService;
import net.bounceme.chronos.chguadalquivir.services.ZonaService;

@Component
@Slf4j
public class EmbalseImporterWriter implements ItemWriter<Embalse> {
	
	@Autowired
	private ZonaService zonaService;
	
	@Autowired
	private EmbalseService embalseService;

    @Override
    public synchronized void write(List<? extends Embalse> items) throws Exception {
        for (Embalse embalse : items) {
        	EmbalseJpa embalseJpa = embalseService.getByCode(embalse.getId());
        	
        	if (Objects.isNull(embalseJpa)) {
        		embalseJpa = new EmbalseJpa();
            	embalseJpa.setCodigo(embalse.getId());
            	embalseJpa.setEmbalse(embalse.getNombre());
            	embalseJpa.setCapacidad(embalse.getCapacidad());
            	embalseJpa.setMen(embalse.getMen());
            	
            	String codZona = embalse.getId().split("-")[0];
            	ZonaJpa zonaJpa = zonaService.getByCode(codZona);
            	if (!Objects.isNull(zonaJpa)) {
            		embalseJpa.setZona(zonaJpa);
            	}
        	}
        	else {
        		embalseJpa.setCapacidad(embalse.getCapacidad());
            	embalseJpa.setMen(embalse.getMen());
        	}
        	
        	embalseService.write(embalseJpa);
            log.info("Writed {}", embalseJpa.toString());
        }
    }

}
