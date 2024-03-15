package net.bounceme.chronos.chguadalquivir.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamSupport;
import org.springframework.beans.factory.annotation.Autowired;

import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;
import net.bounceme.chronos.chguadalquivir.repository.EmbalseRepository;
import net.bounceme.chronos.chguadalquivir.repository.RegistroDiarioEmbalseRepository;
import net.bounceme.chronos.chguadalquivir.repository.RepositoryCollectionCustom;

public class RegistrosEmbalsesItemReader extends ItemStreamSupport implements ItemReader<RegistroDiarioEmbalse> {

	@Autowired
	private EmbalseRepository embalseRepository;
	
	@Autowired
	private RepositoryCollectionCustom repositoryCollectionCustom;
	
	@Autowired
	private RegistroDiarioEmbalseRepository registroDiarioEmbalseRepository;
	
	private List<RegistroDiarioEmbalse> records;

	private Integer index = 0;

	@Override
	public void open(ExecutionContext executionContext) {
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		List<Embalse> embalses = embalseRepository.findAll();
		records = new ArrayList<>();
		
		for (Embalse e : embalses) {
            repositoryCollectionCustom.setCollectionName(e.getId());
            
            Optional<RegistroDiarioEmbalse> oRegistroDiario = registroDiarioEmbalseRepository.findById("2024-03-14");
            if (oRegistroDiario.isPresent()) {
            	records.add(oRegistroDiario.get());
            }
        }

		index = 0;
	}

	/**
	 *
	 */
	@Override
	public RegistroDiarioEmbalse read() {
		RegistroDiarioEmbalse nextElement = null;

		if (index < records.size()) {
			nextElement = records.get(index);
			index++;
		} else {
			index = 0;
		}

		return nextElement;
	}

}
