package net.bounceme.chronos.chguadalquivir.writer;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.repository.EmbalseRepository;
import net.bounceme.chronos.chguadalquivir.repository.RepositoryCollectionCustom;

@Component
@Slf4j
public class EmbalseImporterWriter implements ItemWriter<Embalse> {
	
	@Autowired
	private EmbalseRepository embalseRepository;
	
	@Autowired
	private RepositoryCollectionCustom repositoryCollectionCustom;
	
	@Autowired
	private SimpleDateFormat dateFormat;

    @Override
    public void write(List<? extends Embalse> items) throws Exception {
        for (Embalse e : items) {
            repositoryCollectionCustom.setCollectionName(e.getCodigo());
            
            Optional<Embalse> oEmbalse = embalseRepository.findById(dateFormat.format(e.getFecha()));
            if (!oEmbalse.isPresent()) {
            	// Set id
            	e.setId(dateFormat.format(e.getFecha()));
            
            	log.info("Writing {}", e.toString());
            	embalseRepository.save(e);
            }
            else {
            	log.info("Fecha {} ya registrada para {}", dateFormat.format(e.getFecha()), e.getCodigo());
            }
        }
    }

}
