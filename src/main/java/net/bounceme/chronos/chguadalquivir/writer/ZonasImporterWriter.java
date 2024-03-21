package net.bounceme.chronos.chguadalquivir.writer;

import java.util.List;
import java.util.Objects;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Zona;
import net.bounceme.chronos.chguadalquivir.model.dto.ZonaJpaDTO;
import net.bounceme.chronos.chguadalquivir.services.ZonaService;

@Component
@Slf4j
public class ZonasImporterWriter implements ItemWriter<Zona> {
	
	@Autowired
	private ZonaService zonaService;

    @Override
    public synchronized void write(List<? extends Zona> items) throws Exception {
        for (Zona zona : items) {
            ZonaJpaDTO zonaJpa = zonaService.getByCode(zona.getCodigo());
            if (Objects.isNull(zonaJpa)) {
            	zonaJpa = new ZonaJpaDTO();
                zonaJpa.setCodigo(zona.getCodigo());
                zonaJpa.setNombre(zona.getNombre());
                zonaJpa.setDescripcion(zona.getDescripcion());
                
                zonaJpa = zonaService.write(zonaJpa);
                
                log.info("Writed {}", zonaJpa.toString());
            }
        }
    }

}
