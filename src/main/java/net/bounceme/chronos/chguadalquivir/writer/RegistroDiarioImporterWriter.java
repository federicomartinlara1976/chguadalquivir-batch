package net.bounceme.chronos.chguadalquivir.writer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;
import net.bounceme.chronos.chguadalquivir.model.jpa.EmbalseJpa;
import net.bounceme.chronos.chguadalquivir.model.jpa.RegistroJpa;
import net.bounceme.chronos.chguadalquivir.repository.RegistroDiarioEmbalseRepository;
import net.bounceme.chronos.chguadalquivir.repository.RepositoryCollectionCustom;
import net.bounceme.chronos.chguadalquivir.services.EmbalseService;
import net.bounceme.chronos.chguadalquivir.services.RegistroService;

@Component
@Slf4j
public class RegistroDiarioImporterWriter implements ItemWriter<RegistroDiarioEmbalse> {
	
	@Autowired
	private RegistroDiarioEmbalseRepository registroDiarioEmbalseRepository;
	
	@Autowired
	private RepositoryCollectionCustom repositoryCollectionCustom;
	
	@Autowired
	private EmbalseService embalseService;
	
	@Autowired
	private RegistroService registroService;
	
	@Autowired
	private SimpleDateFormat dateFormat;

    @Override
    public synchronized void write(List<? extends RegistroDiarioEmbalse> items) throws Exception {
        for (RegistroDiarioEmbalse e : items) {
            repositoryCollectionCustom.setCollectionName(e.getCodigo());
            
            Optional<RegistroDiarioEmbalse> oRegistroDiario = registroDiarioEmbalseRepository.findById(dateFormat.format(e.getFecha()));
            if (!oRegistroDiario.isPresent()) {
            	writeRegistroDiario(e);
            }
        }
    }

	private void writeRegistroDiario(RegistroDiarioEmbalse e) throws Exception {
		// Set id
    	e.setId(dateFormat.format(e.getFecha()));
    	registroDiarioEmbalseRepository.save(e);
    	
    	EmbalseJpa embalseJpa = embalseService.getByCode(e.getCodigo());
		
    	RegistroJpa registroJpa = new RegistroJpa();
		registroJpa.setEmbalse(embalseJpa);
		registroJpa.setPorcentaje(e.getPorcentaje());
		registroJpa.setCapacidad(e.getCapacidad());
		registroJpa.setVolumen(e.getVolumen());
		registroJpa.setNivel(e.getNivel());
		registroJpa.setMEN(e.getMEN());
		
		Date fecha = dateFormat.parse(e.getId());
		registroJpa.setFecha(fecha);
		
		registroService.write(registroJpa);
		log.info("Writed {}, {}", e.toString(), registroJpa.toString());
	}

}
