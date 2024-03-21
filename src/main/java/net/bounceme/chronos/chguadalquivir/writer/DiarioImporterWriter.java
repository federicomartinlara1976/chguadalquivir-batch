package net.bounceme.chronos.chguadalquivir.writer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;
import net.bounceme.chronos.chguadalquivir.model.dto.EmbalseJpaDTO;
import net.bounceme.chronos.chguadalquivir.model.dto.RegistroJpaDTO;
import net.bounceme.chronos.chguadalquivir.repository.RegistroDiarioEmbalseRepository;
import net.bounceme.chronos.chguadalquivir.repository.RepositoryCollectionCustom;
import net.bounceme.chronos.chguadalquivir.services.EmbalseService;
import net.bounceme.chronos.chguadalquivir.services.RegistroService;

@Component
@Slf4j
public class DiarioImporterWriter implements ItemWriter<Embalse> {
	
	@Autowired
	private EmbalseService embalseService;
	
	@Autowired
	private RegistroService registroService;
	
	@Autowired
	private RegistroDiarioEmbalseRepository registroDiarioEmbalseRepository;
	
	@Autowired
	private RepositoryCollectionCustom repositoryCollectionCustom;
	
	@Autowired
	private SimpleDateFormat dateFormat;

    @Override
    public synchronized void write(List<? extends Embalse> items) throws Exception {
        for (Embalse embalse : items) {
        	EmbalseJpaDTO embalseJpa = embalseService.getByCode(embalse.getId());
        	
        	repositoryCollectionCustom.setCollectionName(embalseJpa.getCodigo());
        	
        	List<RegistroDiarioEmbalse> registros = registroDiarioEmbalseRepository.findAll();
            
        	for (RegistroDiarioEmbalse registro : registros) {
        		RegistroJpaDTO registroJpa = new RegistroJpaDTO();
        		registroJpa.setEmbalse(embalseJpa);
        		registroJpa.setPorcentaje(registro.getPorcentaje());
        		registroJpa.setVolumen(registro.getVolumen());
        		registroJpa.setNivel(registro.getNivel());
        		
        		Date fecha = dateFormat.parse(registro.getId());
        		registroJpa.setFecha(fecha);
        		
        		registroService.write(registroJpa);
        		log.info("Writed {}", registroJpa.toString());
        	}
        }
    }

}
