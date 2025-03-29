package net.bounceme.chronos.chguadalquivir.writer;

import java.text.SimpleDateFormat;
import java.util.Optional;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioPluviometria;
import net.bounceme.chronos.chguadalquivir.repository.RegistroDiarioPluviometriaRepository;
import net.bounceme.chronos.chguadalquivir.repository.RepositoryCollectionCustom;

@Component
@Slf4j
public class RegistroPluviometriaDiarioImporterWriter implements ItemWriter<RegistroDiarioPluviometria> {
	
	@Autowired
	private RegistroDiarioPluviometriaRepository registroDiarioPluviometriaRepository;
	
	@Autowired
	private RepositoryCollectionCustom repositoryCollectionCustom;
	
	@Autowired
	private SimpleDateFormat dateFormat;

    @Override
    @SneakyThrows
    public synchronized void write(Chunk<? extends RegistroDiarioPluviometria> items) {
    	
        for (RegistroDiarioPluviometria e : items) {
        	repositoryCollectionCustom.setCollectionName("PL" + "-" + e.getZona() + "-" + e.getCodigo());
        	
        	Optional<RegistroDiarioPluviometria> oRegistroDiario = registroDiarioPluviometriaRepository.findById(dateFormat.format(e.getFecha()));
            if (!oRegistroDiario.isPresent()) {
            	writeRegistroDiario(e);
            }
        }
    }
    
    @SneakyThrows
    private void writeRegistroDiario(RegistroDiarioPluviometria e) {
		// Set id
    	e.setId(dateFormat.format(e.getFecha()));
    	registroDiarioPluviometriaRepository.save(e);
    	log.info("Writed {}", e.toString());
	}
}
